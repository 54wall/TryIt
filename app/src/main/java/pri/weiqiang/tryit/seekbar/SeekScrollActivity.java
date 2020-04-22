package pri.weiqiang.tryit.seekbar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebSettings;
import android.widget.SeekBar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import java.io.File;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.seekbar.widget.ScrollBindHelper;

/**
 * https://blog.csdn.net/drkcore/article/details/50999932
 */
public class SeekScrollActivity extends BaseActivity {
    private final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_scroll);
//		TextView tv = findViewById(R.id.textView_main_content);
        //必须动态申请权限，否则webview无法显示，并且不会提示任何与权限相关的信息，网页直接显示net::err_accss_denied
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

            return;
        }
        NoScrollWebView wb = findViewById(R.id.wb);
        wb.loadUrl("file:" + Environment.getExternalStorageDirectory() + File.separator + "/aihub/huanye.txt");//方法二，可行
        WebSettings webSettings = wb.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        SeekBar seekBar = findViewById(R.id.seekBar_main_scrollThumb);
        NestedScrollView scrollView = findViewById(R.id.nestedScrollView_frag_edit);

        ScrollBindHelper.bind(seekBar, scrollView);
    }
}

