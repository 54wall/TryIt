package pri.weiqiang.tryit.webview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * webview显示大于几MB文本txt
 */
public class WebviewActivity extends BaseActivity {

    private static final int WEBVIEW_PERMISSIONS_REQUEST_READ_MEDIA = 1;
    private CustomWebView wb;
    private String TAG = WebviewActivity.class.getSimpleName();
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hookWebView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_webview);
        pb = findViewById(R.id.pb);
        wb = findViewById(R.id.wb);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WEBVIEW_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            loadWebView();
        }

        wb.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e(TAG, "cotent.getHeight()===" + wb.getHeight());//返回的是View的高度
                Log.e(TAG, "cotent.getContentHeight()===" + wb.getContentHeight());//返回的是Html的高度
                Log.e(TAG, "cotent.getMeasuredHeight()===" + wb.getMeasuredHeight());//返回的是View的高度

            }
        });
        WebSettings webSettings = wb.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        wb.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WEBVIEW_PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    loadWebView();
                }
                break;

            default:
                break;
        }
    }

    /**
     * 注意必须放置在setContentView前
     * java.lang.UnsupportedOperationException: For security reasons, WebView is not allowed in privileged processes
     * 因难以避免WebView存在安全漏洞，系统遭受攻击，Android不允许特权进程应用使用WebView。
     * https://www.cnblogs.com/genggeng/p/7716482.html
     */
    private void hookWebView() {

        int sdkInt = Build.VERSION.SDK_INT;
        try {
            Class<?> factoryClass = Class.forName("android.webkit.WebViewFactory");
            Field field = factoryClass.getDeclaredField("sProviderInstance");
            field.setAccessible(true);
            Object sProviderInstance = field.get(null);
            if (sProviderInstance != null) {
                Log.i(TAG, "sProviderInstance isn't null");
                return;
            }

            Method getProviderClassMethod;
            if (sdkInt > 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
            } else if (sdkInt == 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
            } else {
                Log.i(TAG, "Don't need to Hook WebView");
                return;
            }
            getProviderClassMethod.setAccessible(true);
            Class<?> factoryProviderClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
            Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> delegateConstructor = delegateClass.getDeclaredConstructor();
            delegateConstructor.setAccessible(true);
            if (sdkInt < 26) {//低于Android O版本
                Constructor<?> providerConstructor = factoryProviderClass.getConstructor(delegateClass);
                if (providerConstructor != null) {
                    providerConstructor.setAccessible(true);
                    sProviderInstance = providerConstructor.newInstance(delegateConstructor.newInstance());
                }
            } else {
                Field chromiumMethodName = factoryClass.getDeclaredField("CHROMIUM_WEBVIEW_FACTORY_METHOD");
                chromiumMethodName.setAccessible(true);
                String chromiumMethodNameStr = (String) chromiumMethodName.get(null);
                if (chromiumMethodNameStr == null) {
                    chromiumMethodNameStr = "create";
                }
                Method staticFactory = factoryProviderClass.getMethod(chromiumMethodNameStr, delegateClass);
                if (staticFactory != null) {
                    sProviderInstance = staticFactory.invoke(null, delegateConstructor.newInstance());
                }
            }

            if (sProviderInstance != null) {
                field.set("sProviderInstance", sProviderInstance);
                Log.i(TAG, "Hook success!");
            } else {
                Log.i(TAG, "Hook failed!");
            }
        } catch (Throwable e) {
            Log.w(TAG, e);
        }
    }

    public void loadWebView() {
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 加载完成
                super.onPageFinished(view, url);
                Log.e(TAG, "onPageFinished wb.computeVerticalScrollRange():" + wb.computeVerticalScrollRange());
                //不*3无法滚动到底
//                wb.scrollTo(0, wb.computeVerticalScrollRange());
                Log.e(TAG, "222 wb.computeVerticalScrollRange():" + wb.computeVerticalScrollRange());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 加载开始
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, "onPageStarted wb.computeVerticalScrollRange():" + wb.computeVerticalScrollRange());

            }
        });

        wb.getSettings().setAllowFileAccess(true);
        wb.getSettings().setAllowContentAccess(true);
//        wb.loadUrl("file:\\mnt\\sdcard\\aihub\\huanye.txt");//方法一，可行
        wb.loadUrl("file:" + Environment.getExternalStorageDirectory() + File.separator + "/aihub/huanye.html");//方法二，可行
    }

    class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void hello(String str) {
            Log.e(TAG, "JS调用了Android的hello方法");
            //这里可以改变activity中的UI控件的状态
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "18555232355555 wb.computeVerticalScrollRange():" + wb.computeVerticalScrollRange());
                }
            });
        }
    }
}
