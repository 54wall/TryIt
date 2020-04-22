package pri.weiqiang.tryit.install.core;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import pri.weiqiang.tryit.install.InstallConstants;

public class ApkManageService extends Service {

    @Override
    public void onCreate() {
        createForeground();
        super.onCreate();
    }

    private void createForeground() {
        //使用兼容版本
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "");
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //设置通知栏的标题内容
        builder.setContentTitle("zhao Install Service");
        //创建通知
        Notification notification = builder.build();
        //创建前台服务
        this.startForeground(InstallConstants.NOTIFICATION_DOWNLOAD_PROGRESS_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(InstallConstants.TAG, "onStartCommand");
        if (intent == null) {
            return InstallConstants.returnValue;
        }
        switch (intent.getAction()) {
            case InstallConstants.ACTION_INSTALL_SILENT:
                String filePath = intent.getStringExtra(InstallConstants.EXTRA_APK_PATH);
                String pack = intent.getStringExtra(InstallConstants.EXTRA_PACKAGE);
                boolean isOpen = intent.getBooleanExtra(InstallConstants.EXTRA_IS_OPEN, false);
                ApkOperateManager.installApkSilent(this, filePath, pack, isOpen);
                break;
            case InstallConstants.ACTION_UNINSTALL_SILENT:
                String packUninstall = intent.getStringExtra(InstallConstants.EXTRA_PACKAGE);
                ApkOperateManager.uninstallApkSilent(this, packUninstall);
                break;
            case InstallConstants.ACTION_UNINSTALL_INSTALL_SILENT:
                String path = intent.getStringExtra(InstallConstants.EXTRA_APK_PATH);
                String packUnIn = intent.getStringExtra(InstallConstants.EXTRA_PACKAGE);
                boolean isOpenUnIn = intent.getBooleanExtra(InstallConstants.EXTRA_IS_OPEN, false);
                ApkOperateManager.uninstallInstallSilent(this, path, packUnIn, isOpenUnIn);
                break;
        }

        return InstallConstants.returnValue;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
