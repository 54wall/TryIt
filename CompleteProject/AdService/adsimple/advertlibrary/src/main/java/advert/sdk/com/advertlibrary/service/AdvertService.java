package advert.sdk.com.advertlibrary.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

import advert.sdk.com.advertlibrary.constant.AdvertConstant;
import advert.sdk.com.advertlibrary.engine.AdvertManager;
import advert.sdk.com.advertlibrary.utils.AdvertUtils;

public class AdvertService extends Service {
    private final String TAG = AdvertService.class.getSimpleName();
    AdvertManager advertManager;
    public static final String CHANNEL_ID_STRING = "AdvertService001";


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        super.onCreate();
        AdvertConstant.PATH_PROJECT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ad";
        File file = new File(AdvertConstant.PATH_PROJECT);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            Log.w(TAG, AdvertConstant.PATH_PROJECT + " mkdirs:" + mkdirs);
        }
        AdvertConstant.PATH_ADS_ZIP = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ad/" + "ads.zip";
        AdvertConstant.PATH_ADS = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ad/" + "ads";
        AdvertConstant.PATH_ADS_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ad/" + "ads/";
        AdvertConstant.PATH_ADS_CONF = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ad/ads/" + "Ads.json";
        Log.e(TAG, "AdvertConstant.PATH_ADS_ZIP:" + AdvertConstant.PATH_ADS_ZIP);
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, "AndroidServer", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }

        try {
            if (advertManager == null) {
                advertManager = new AdvertManager(this);
            }
        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand() ");

        if (advertManager == null) {
            advertManager = new AdvertManager(this);
        }
        AdvertUtils.getInstance().showLoopView(AdvertConstant.AD_SEC_FULL);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy()");
        try {
            advertManager.destoryPresentation();
        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage());
        }

        super.onDestroy();

    }


}
