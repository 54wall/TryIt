package pri.weiqiang.tryit.print.one;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import pri.weiqiang.tryit.R;

public class PrintImgInWebviewActivity extends AppCompatActivity {

    private boolean mDataLoaded;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_img_in_webview);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //用于js调用Android
        webSettings.setJavaScriptEnabled(true);
        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebChromeClient(new chromClient());
        //访问Android assets文件夹内的
        String url = "file:///android_asset/test.html";
        //访问网页Html
//    String url="http://192.168.1.121:8080/jsandroid/index.html"；
        runWebView(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (mDataLoaded) {
            getMenuInflater().inflate(R.menu.print_custom_content, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_print) {
            print();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    private void print() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        printManager.print("MotoGP stats",
                webView.createPrintDocumentAdapter(), null);
    }

    private void runWebView(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    private class chromClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //页面加载完成执行的操作
                String path = "file://" + Environment.getExternalStorageDirectory() + File.separator + "123.jpg";
                String action = "javascript:aa('" + path + "')";
//                new AlertDialog.Builder(PrintImgInWebviewActivity.this)
//                        .setMessage(action)
//                        .show();
                runWebView(action);
                // 加载完成，显示打印选项。
                mDataLoaded = true;
                invalidateOptionsMenu();
            }
            super.onProgressChanged(view, newProgress);

        }
    }
}

