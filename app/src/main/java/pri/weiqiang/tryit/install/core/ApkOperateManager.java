package pri.weiqiang.tryit.install.core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

import pri.weiqiang.tryit.install.InstallConstants;

/**
 * APK安装器，其中报错为正常现象，将调用安卓系统FrameWork中的代码，而非SDK中代码，所以会报错
 * 有时会存在编译不过，再次进行编译即可
 */
public class ApkOperateManager {

    public static String fileName;
    private static String TAG = ApkOperateManager.class.getSimpleName();
    private Context contextInstall;

    //安装apk
    public static void installApk1(Context context, String fileName) {
        File apkFile = new File(fileName);
        if (apkFile.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory("android.intent.category.DEFAULT");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //如果manifest中写的是fileprovider这里也要用fileprovider，其实只要是一模一样就可以,随便写
                Uri contentUri = FileProvider.getUriForFile(context, "pri.weiqiang.home.provider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }
    }

    public static void installApk2(Context context, String fileName) {
        File apkFile = new File(fileName);
        if (apkFile.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory("android.intent.category.DEFAULT");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //如果manifest中写的是fileprovider这里也要用fileprovider，其实只要是一模一样就可以,随便写 使用BuildConfig.APPLICATION_ID需要更换包名
//                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", apkFile);
                Uri contentUri = FileProvider.getUriForFile(context, "pri.weiqiang.home.provider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }
    }

    //常规卸载apk
    public static void uninstallApk(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //普通卸载再安装
    public static void uninstallInstall(Context context, String packageName, String fileName) {

        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ApkOperateManager.fileName = fileName;
        intent.putExtra("pri.weiqiang.home.ApkOperateManager", fileName);
        context.startActivity(intent);

    }


    //静默安装
    public static void installApkSilent(Context context, String fileName, String packageName, boolean isopen) {
        Log.e(TAG, "installApkSilent");
        File file = new File(fileName);
        int installFlags = 0;
        if (!file.exists()) {
            return;
        }
        installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;
        PackageManager pm = context.getPackageManager();
        IPackageInstallObserver observer = new PakcageInstallObserver(context, packageName, isopen);
        pm.installPackage(Uri.fromFile(file), observer, installFlags, packageName);
    }

    //静默卸载
    public static void uninstallApkSilent(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        IPackageDeleteObserver observer = new PackageDeleteObserver(context);
        try {
            pm.deletePackage(packageName, observer, 0);
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "Exception:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //静默卸载再静默安装
    public static void uninstallInstallSilent(Context context, String fileName, String packageName, boolean isopen) {
        Log.e(TAG, "uninstallInstallSilent");
        PackageManager pm = context.getPackageManager();
        IPackageDeleteObserver observer = new uninstallInstallObserver(context, fileName, packageName, isopen);
        try {
            pm.deletePackage(packageName, observer, 0);
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "Exception:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //2秒后回馈结果
    private static void sendResultCode(Context context, int returnCode,
                                       String packageName) {
        Log.e(TAG, "sendResultCode");
        Intent resultIntent = new Intent(InstallConstants.RESULT_EXCUTOR);
        resultIntent.putExtra(InstallConstants.EXTRA_RESULT, returnCode);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Service.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + 1000, pendingIntent);
    }


    //静默安装回调
    private static class PakcageInstallObserver extends IPackageInstallObserver.Stub {
        private static final PackageManager Intent = null;
        Context context;
        String packageName;
        boolean isOpen;

        PakcageInstallObserver(Context conext, String packageName, boolean isOpen) {
            this.context = conext;
            this.packageName = packageName;
            this.isOpen = isOpen;
        }

        @Override
        public void packageInstalled(String packageName, int returnCode)
                throws RemoteException {
            Log.e(TAG, "returnCode = " + returnCode);//返回1代表安装成功
            sendResultCode(context, returnCode, packageName);
            if (isOpen) {
                // 启动目标应用
                PackageManager packManager = context.getPackageManager();
                // 这里的packageName就是从上面得到的目标apk的包名
                Intent intent = packManager.getLaunchIntentForPackage(packageName);
                context.startActivity(intent);
            }
        }
    }

    //静默卸载回调
    private static class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
        Context context;

        PackageDeleteObserver(Context context) {
            this.context = context;
        }

        @Override
        public void packageDeleted(String packageName, int returnCode) {
            Log.e(TAG, "returnCode = " + returnCode);//返回1代表卸载成功
            sendResultCode(context, returnCode, packageName);
        }

    }

    //静默卸载再安装回调
    private static class uninstallInstallObserver extends IPackageDeleteObserver.Stub {
        Context context;
        String fileName;
        String pakcageName;
        boolean isopen;

        uninstallInstallObserver(Context context, String fileName, String pakcageName, boolean isopen) {
            this.context = context;
            this.fileName = fileName;
            this.pakcageName = pakcageName;
            this.isopen = isopen;
        }

        @Override
        public void packageDeleted(String packageName, int returnCode) {
            Log.e(TAG, "uninstallInstallObserver returnCode = " + returnCode);//返回1代表卸载成功
            File file = new File(fileName);
            int installFlags = 0;
            if (!file.exists()) {
                return;
            }
            installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;
            PackageManager pm = context.getPackageManager();
            IPackageInstallObserver observer = new PakcageInstallObserver(context, pakcageName, isopen);
            pm.installPackage(Uri.fromFile(file), observer, installFlags, pakcageName);

        }

    }
}