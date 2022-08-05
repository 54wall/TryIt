package pri.weiqiang.tryit.h5;

import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import pri.weiqiang.tryit.R;

/**
 * https://blog.csdn.net/qq_31278925/article/details/124291514
 */
public class H5BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView wvTest;
    private Button btGreen;
    private Button btColor;
    private Button btReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_base);
        initView();
    }

    private void initView() {
        wvTest = findViewById(R.id.wvTest);
        btGreen = findViewById(R.id.btGreen);
        btColor = findViewById(R.id.btColor);
        btReturn = findViewById(R.id.btReturn);

        btGreen.setOnClickListener(this);
        btColor.setOnClickListener(this);
        btReturn.setOnClickListener(this);

        WebSettings webSettings = wvTest.getSettings();
        webSettings.setJavaScriptEnabled(true);
        JsInterface jsInterface = new JsInterface(H5BaseActivity.this);
        wvTest.addJavascriptInterface(jsInterface, jsInterface.toString());

        wvTest.loadUrl("file:///android_asset/JsTest.html");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btGreen:
                wvTest.loadUrl("javascript:setColor()");
                break;
            case R.id.btColor:
                wvTest.loadUrl("javascript:setColorAndText('#FFFFE0','text3:这是修改后的文字！')");
                break;
            case R.id.btReturn:
                wvTest.evaluateJavascript("sum(100,2)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(H5BaseActivity.this, "sum返回的结果为：" + s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }


}