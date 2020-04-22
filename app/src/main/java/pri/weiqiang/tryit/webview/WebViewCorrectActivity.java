package pri.weiqiang.tryit.webview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class WebViewCorrectActivity extends BaseActivity {
    private final int REQUEST_PERMISSION = 1;
    private String TAG = WebViewCorrectActivity.class.getSimpleName();
    private CustomWebView mWebView;
    private WebAppInterface mMyJavaScriptInterface;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //必须动态申请权限，否则webview无法显示，并且不会提示任何与权限相关的信息，网页直接显示net::err_accss_denied
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

            return;
        }
        pb = findViewById(R.id.pb);
        mWebView = findViewById(R.id.wb);
        mWebView.setVisibility(View.INVISIBLE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mMyJavaScriptInterface = new WebAppInterface();
        mWebView.addJavascriptInterface(mMyJavaScriptInterface, "AndroidFunction");
        mWebView.setWebViewClient(new WebClient());
//        mWebView.loadUrl("http://exploiter.ivydad.com/invite/newFriend/e2cf0e15547711e68ed590e2baab7e5c");
        mWebView.loadUrl("file:" + Environment.getExternalStorageDirectory() + File.separator + "aihub/huanye.txt");//方法二，可行

    }

    class WebClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:AndroidFunction.resize(document.body.scrollHeight)");
        }
    }

    /**
     * WebView interface to communicate with Javascript
     */
    public class WebAppInterface {

        @JavascriptInterface
        public void resize(final float height) {
            float webViewHeight = (height * getResources().getDisplayMetrics().density);
            Log.e(TAG, "webView 高度为 : " + webViewHeight);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.scrollTo(0, (int) webViewHeight);
                    mWebView.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
            });
        }
    }
}