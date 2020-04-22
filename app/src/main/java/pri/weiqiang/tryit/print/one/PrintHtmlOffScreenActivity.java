package pri.weiqiang.tryit.print.one;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.tryit.R;

/**
 * @author ldm
 * @description：
 * @date 2016-4-28 上午9:39:55
 */
public class PrintHtmlOffScreenActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_html_off_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.print_custom_content, menu);
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

    private void print() {
        mWebView = new WebView(this);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                doPrint();
            }
        });

        mWebView.loadUrl("file:///android_res/raw/print_html.html");
    }

    private void doPrint() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter adapter = new PrintDocumentAdapter() {
            private final PrintDocumentAdapter mWrappedInstance = mWebView
                    .createPrintDocumentAdapter();

            @Override
            public void onStart() {
                mWrappedInstance.onStart();
            }

            @SuppressLint("WrongCall")
            @Override
            public void onLayout(PrintAttributes oldAttributes,
                                 PrintAttributes newAttributes,
                                 CancellationSignal cancellationSignal,
                                 LayoutResultCallback callback, Bundle extras) {
                mWrappedInstance.onLayout(oldAttributes, newAttributes,
                        cancellationSignal, callback, extras);
            }

            @Override
            public void onWrite(PageRange[] pages,
                                ParcelFileDescriptor destination,
                                CancellationSignal cancellationSignal,
                                WriteResultCallback callback) {
                mWrappedInstance.onWrite(pages, destination,
                        cancellationSignal, callback);
            }

            @Override
            public void onFinish() {
                mWrappedInstance.onFinish();
                mWebView.destroy();
                mWebView = null;
            }
        };

        printManager.print("MotoGP stats", adapter, null);
    }
}
