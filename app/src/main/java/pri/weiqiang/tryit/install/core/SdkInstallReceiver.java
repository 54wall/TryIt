package pri.weiqiang.tryit.install.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/*
 * 不能和静默自定义的ApkManageReceiver组合，*/
public class SdkInstallReceiver extends BroadcastReceiver {
    private String TAG = SdkInstallReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {

            case Intent.ACTION_PACKAGE_ADDED: {
                Log.e(TAG, "安装成功:" + intent.getData().getSchemeSpecificPart());
                break;

            }
            case Intent.ACTION_PACKAGE_REMOVED: {
                Log.e(TAG, "卸载成功:" + intent.getData().getSchemeSpecificPart());

                String fileName = intent.getStringExtra("pri.weiqiang.home.ApkOperateManager");
                Log.e(TAG, "普通卸载 fileName:" + fileName);
                if (ApkOperateManager.fileName != null) {
                    ApkOperateManager.installApk1(context, ApkOperateManager.fileName);
                }

                break;

            }
            case Intent.ACTION_PACKAGE_REPLACED: {
                Log.e(TAG, "替换成功" + intent.getData().getSchemeSpecificPart());
                break;

            }
        }
    }
}
