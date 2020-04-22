package pri.weiqiang.tryit.print.two;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * https://github.com/mannix-lei/android-Printer
 */
public class PrintTwoActivity extends BaseActivity {
    private Button bt;
    private String path = Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator;
    private String pdfName = "test.pdf";//.pdf Mopria_DiscoverEnablePrint

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_two);
        bt = findViewById(R.id.print);
        //1.匿名内部类
        bt.setOnClickListener(new MyListener());
    }

    private void doPdfPrint(String filePath) {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(PrintAttributes.COLOR_MODE_COLOR);
        printManager.print("jobName", new MyPrintPdfAdapter(this, filePath), builder.build());
    }

    public void button_Click(View view) {
        Log.i("指定onClick属性方式", "点击事件");
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (PrintTwoActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        PrintTwoActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    }
                }
            }

            doPdfPrint(path + pdfName);//doPdfPrint.xml
        }
    }
}
