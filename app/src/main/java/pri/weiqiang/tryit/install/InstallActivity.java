package pri.weiqiang.tryit.install;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.install.core.ApkOperateManager;

/**
 * @author zhao
 * 测试Activity
 */
public class InstallActivity extends BaseActivity {

    private String TAG = InstallActivity.class.getSimpleName();
    private String path = Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator;
    private String apkName = "MyApplication.apk";
    private String packageName = "com.example.myapplication";
    private boolean isOpen = false;
    private ResultReceiver receiver;

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter(InstallConstants.RESULT_EXCUTOR);
        receiver = new ResultReceiver();
        registerReceiver(receiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        Button btnKillProcess = findViewById(R.id.btn_kill_process);
        btnKillProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        Intent intent = getIntent();
        String value = intent.getStringExtra(InstallConstants.EXTRA_MODE);
        if (value != null) {
            switch (value) {
                case InstallConstants.EXTRA_MODE_INSTALL_SILENT:
                    Log.e(TAG, "EXTRA_MODE_INSTALL_SILENT");
                    packageName = intent.getStringExtra(InstallConstants.EXTRA_PACKAGE);
                    path = intent.getStringExtra(InstallConstants.EXTRA_APK_PATH);
                    apkName = intent.getStringExtra(InstallConstants.EXTRA_APK_NAME);
                    isOpen = intent.getBooleanExtra(InstallConstants.EXTRA_IS_OPEN, true);
                    Log.e(TAG, "packageName:" + packageName + " apkName" + apkName + " path" + path + " isOpen" + isOpen);
                    sendInstallSilentBroadcast(path + apkName, packageName, isOpen);
                    break;
                case InstallConstants.EXTRA_MODE_UNINSTALL_SILENT:
                    packageName = intent.getStringExtra(InstallConstants.EXTRA_PACKAGE);
                    sendUnInstallSilentBroadcast(packageName);
                    break;
                case InstallConstants.EXTRA_MODE_INSTALL:
                    path = intent.getStringExtra(InstallConstants.EXTRA_APK_PATH);
                    apkName = intent.getStringExtra(InstallConstants.EXTRA_APK_NAME);
                    ApkOperateManager.installApk1(InstallActivity.this, path + apkName);
                    break;
                case InstallConstants.EXTRA_MODE_UNINSTALL:
                    packageName = intent.getStringExtra(InstallConstants.EXTRA_PACKAGE);
                    ApkOperateManager.uninstallApk(InstallActivity.this, packageName);
                    break;
                default:
                    break;

            }
        }
    }

    //静默安装
    public void installSilent(View view) {
        sendInstallSilentBroadcast(path + apkName, packageName, isOpen);
    }

    //普通安装
    public void installOne(View view) {
        ApkOperateManager.installApk1(InstallActivity.this, path + apkName);
    }

    //普通安装
    public void installTwo(View view) {
        ApkOperateManager.installApk2(InstallActivity.this, path + apkName);
    }

    //静默卸载
    public void unInstallSilent(View view) {
        sendUnInstallSilentBroadcast(packageName);
        sendUnInstallSilentBroadcast(packageName);
        sendUnInstallSilentBroadcast(packageName);
        sendUnInstallSilentBroadcast(packageName);
    }

    //普通卸载
    public void unintall(View view) {
        ApkOperateManager.uninstallApk(InstallActivity.this, packageName);
    }

    private void showResult(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    //普通先卸载再安装
    public void unintallInstall(View view) {
        ApkOperateManager.uninstallInstall(InstallActivity.this, packageName, path + apkName);
    }

    //静默卸载再静默安装
    public void unintallInstallSlient(View view) {
        sendInstallUninstallSilentBroadcast(path + apkName, packageName, isOpen);
    }

    /**
     * 发送静默卸载再静默安装请求
     *
     * @param apkPath     安装包本地地址
     * @param packageName 包名
     * @param isOpen      安装成功后是否打开
     */
    private void sendInstallUninstallSilentBroadcast(String apkPath, String packageName, boolean isOpen) {
        Intent mIntent = new Intent(InstallConstants.MANAGE_UNINSTALL_INSTALL_SILENT);
        mIntent.putExtra(InstallConstants.EXTRA_APK_PATH, apkPath);
        mIntent.putExtra(InstallConstants.EXTRA_PACKAGE, packageName);
        mIntent.putExtra(InstallConstants.EXTRA_IS_OPEN, isOpen);
        sendBroadcast(mIntent);
    }

    /**
     * 发送静默安装请求
     *
     * @param apkPath     安装包本地地址
     * @param packageName 包名
     * @param isOpen      安装成功后是否打开
     */
    private void sendInstallSilentBroadcast(String apkPath, String packageName, boolean isOpen) {
        Intent mIntent = new Intent(InstallConstants.MANAGE_INSTALL_SILENT);
        mIntent.putExtra(InstallConstants.EXTRA_APK_PATH, apkPath);
        mIntent.putExtra(InstallConstants.EXTRA_PACKAGE, packageName);
        mIntent.putExtra(InstallConstants.EXTRA_IS_OPEN, isOpen);
        sendBroadcast(mIntent);
    }

    /**
     * 发送静默卸载请求
     *
     * @param packageName 卸载apk包名
     */
    private void sendUnInstallSilentBroadcast(String packageName) {
        Intent mIntent = new Intent(InstallConstants.MANAGE_UNINSTALL_SILENT);
        mIntent.putExtra(InstallConstants.EXTRA_PACKAGE, packageName);
        sendBroadcast(mIntent);
    }

    /**
     * 接收执行结果
     * 注册广播Constants.EXTRA_RESULT {@link InstallConstants}
     */
    class ResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //1表示执行成功
            int result = intent.getIntExtra(InstallConstants.EXTRA_RESULT, 0);
            if (result == 1) {
                showResult("操作成功");
            } else {
                showResult("操作失败:" + result);
            }
        }
    }
}