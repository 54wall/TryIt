package com.example.android.systemupdatersample.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PackageUtil {
    private static final String TAG = PackageUtil.class.getSimpleName();

    public static String getImei(Context context) {
        String imei = "";
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            imei = telephonyMgr.getDeviceId();
            if (imei == null) {
                imei = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }


    /**
     * 获取指定包名的版本号
     *
     * @param context     本应用程序上下文
     * @param packageName 你想知道版本信息的应用程序的包名
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
            int version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getVersionCode:" + e.getMessage());
            return 0;
        }
    }

    /**
     * 获取渠道名
     *
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context context) {
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("APK_CHANNEL"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 获取版本号，对没有安装的apk返回“null”，单独一个方法
     */
    public static String getPackageVersionName(Context context, String packName) {
        String versionName = "null";
        try {
            versionName = context.getPackageManager().getPackageInfo(packName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}