package pri.weiqiang.tryit.print.one;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.tryit.R;

/**
 * 打印Html页面
 *
 * @author ldm
 * @description：
 * @date 2016-4-28 上午9:28:25
 */
public class PrintHtmlActivity extends AppCompatActivity {

    private WebView mWebView;

    private boolean mDataLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_html);
        mWebView = findViewById(R.id.web_view);

        // 重要：只有在加载页面后才能启用打印选项。
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 加载完成，显示打印选项。
                mDataLoaded = true;
                invalidateOptionsMenu();
            }
        });
        // 加载本地Html文件
//		mWebView.loadUrl("file:///android_res/raw/print_html.html");
        mWebView.loadUrl("https://www.baidu.com/");
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
                mWebView.createPrintDocumentAdapter(), null);
    }
}
