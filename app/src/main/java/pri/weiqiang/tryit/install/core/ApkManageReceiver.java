package pri.weiqiang.tryit.install.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pri.weiqiang.tryit.install.InstallConstants;


public class ApkManageReceiver extends BroadcastReceiver {

    private String TAG = ApkManageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, " onReceive 接受静态广播 ");
        switch (intent.getAction()) {
            case InstallConstants.BOOT_COMPLETED: {
                Log.e(TAG, " onReceive BOOT_COMPLETED ");
                Intent intentCompleted = new Intent(context, ApkManageService.class);
                intentCompleted.setAction(InstallConstants.START_SERVICE);
                context.startService(intentCompleted);
                break;
            }
            case InstallConstants.MANAGE_INSTALL_SILENT: {
                Log.e(TAG, " onReceive MANAGE_INSTALL_SILENT ");
                //判断所需字段是否完整
                if (!intent.hasExtra(InstallConstants.EXTRA_PACKAGE) || !intent.hasExtra(InstallConstants.EXTRA_APK_PATH)) {
                    return;
                }
                Intent intentInSilent = new Intent(context, ApkManageService.class);
                intentInSilent.setAction(InstallConstants.ACTION_INSTALL_SILENT);
                intentInSilent.putExtra(InstallConstants.EXTRA_PACKAGE, intent.getStringExtra(InstallConstants.EXTRA_PACKAGE));
                intentInSilent.putExtra(InstallConstants.EXTRA_APK_PATH, intent.getStringExtra(InstallConstants.EXTRA_APK_PATH));
                intentInSilent.putExtra(InstallConstants.EXTRA_IS_OPEN, intent.getBooleanExtra(InstallConstants.EXTRA_IS_OPEN, false));
                context.startService(intentInSilent);
                break;
            }
            case InstallConstants.MANAGE_UNINSTALL_SILENT: {
                Log.e(TAG, " onReceive MANAGE_UNINSTALL_SILENT ");
                Intent intentUnSilent = new Intent(context, ApkManageService.class);
                intentUnSilent.setAction(InstallConstants.ACTION_UNINSTALL_SILENT);
                intentUnSilent.putExtra(InstallConstants.EXTRA_PACKAGE, intent.getStringExtra(InstallConstants.EXTRA_PACKAGE));
                context.startService(intentUnSilent);
                break;

            }
            case InstallConstants.MANAGE_UNINSTALL_INSTALL_SILENT: {
                Log.e(TAG, " onReceive MANAGE_UNINSTALL_INSTALL_SILENT ");
                Intent intentUnInSilent = new Intent(context, ApkManageService.class);
                intentUnInSilent.setAction(InstallConstants.ACTION_UNINSTALL_INSTALL_SILENT);
                intentUnInSilent.putExtra(InstallConstants.EXTRA_PACKAGE, intent.getStringExtra(InstallConstants.EXTRA_PACKAGE));
                intentUnInSilent.putExtra(InstallConstants.EXTRA_APK_PATH, intent.getStringExtra(InstallConstants.EXTRA_APK_PATH));
                intentUnInSilent.putExtra(InstallConstants.EXTRA_IS_OPEN, intent.getBooleanExtra(InstallConstants.EXTRA_IS_OPEN, false));
                context.startService(intentUnInSilent);
                break;

            }
        }
    }
}
