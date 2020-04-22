package pri.weiqiang.tryit.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.constants.Constants;

/**
 * 验证静态广播在杀死目标application进程后依然可以接收，已经验证
 */
public class BroadcastActivity extends BaseActivity {
    private String apkPath = Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator;
    private String apkName = "MyApplication.apk";
    private String packageName = "com.example.myapplication";
    private boolean isOpen = true;//静默安装后，是否自动打开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        Button btnBroadCast = findViewById(R.id.btn_broadcast);
        btnBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInstallSilentBroadcast(apkPath + apkName, packageName, isOpen);
            }
        });
    }

    /**
     * 发送静默安装请求
     *
     * @param apkPath     安装包本地地址
     * @param packageName 包名
     * @param isOpen      安装成功后是否打开
     */
    private void sendInstallSilentBroadcast(String apkPath, String packageName, boolean isOpen) {
        Intent mIntent = new Intent(Constants.MANAGE_INSTALL_SILENT);
        mIntent.putExtra(Constants.EXTRA_APK_PATH, apkPath);
        mIntent.putExtra(Constants.EXTRA_PACKAGE, packageName);
        mIntent.putExtra(Constants.EXTRA_IS_OPEN, isOpen);
        sendBroadcast(mIntent);
    }
}
