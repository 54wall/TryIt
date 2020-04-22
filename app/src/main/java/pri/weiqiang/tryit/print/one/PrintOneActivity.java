package pri.weiqiang.tryit.print.one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * https://github.com/HandsomeBoy11/AndroidPrint1/
 */
public class PrintOneActivity extends BaseActivity implements OnClickListener {
    private Button bitmap_btn;
    private Button layout_btn;
    private Button html_btn;
    private Button off_screen_btn;
    private Button btnPdf;
    private Button mBtnHtmlImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_one);
        initViews();
    }

    private void initViews() {
        this.bitmap_btn = findViewById(R.id.bitmap_btn);
        this.layout_btn = findViewById(R.id.layout_btn);
        this.html_btn = findViewById(R.id.html_btn);
        this.mBtnHtmlImg = findViewById(R.id.btn_html_img);
        this.off_screen_btn = findViewById(R.id.off_screen_btn);
        this.btnPdf = findViewById(R.id.btn_pdf);
        this.bitmap_btn.setOnClickListener(this);
        this.layout_btn.setOnClickListener(this);
        this.html_btn.setOnClickListener(this);
        this.off_screen_btn.setOnClickListener(this);
        this.btnPdf.setOnClickListener(this);
        this.mBtnHtmlImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bitmap_btn:
                startIntent(PrintBitmapActivity.class);
                break;
            case R.id.layout_btn:
                startIntent(PrintLyaoutActivity.class);
                break;
            case R.id.html_btn:
                startIntent(PrintHtmlActivity.class);
                break;
            case R.id.btn_html_img:
                startIntent(PrintImgInWebviewActivity.class);
                break;
            case R.id.off_screen_btn:
                startIntent(PrintHtmlOffScreenActivity.class);
                break;
            case R.id.btn_pdf:
                startIntent(PrintPdfOneActivity.class);
                break;
        }
    }

    private void startIntent(Class<?> tagert) {
        Intent in = new Intent(this, tagert);
        startActivity(in);
    }
}
