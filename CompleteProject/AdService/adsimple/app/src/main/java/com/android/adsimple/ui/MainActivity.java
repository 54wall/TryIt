package com.android.adsimple.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.adsimple.R;

import advert.sdk.com.advertlibrary.constant.AdvertConstant;
import advert.sdk.com.advertlibrary.engine.AdvertEngine;
import advert.sdk.com.advertlibrary.utils.AdvertUtils;
import advert.sdk.com.advertlibrary.utils.PermissionUtils;

public class MainActivity extends Activity implements View.OnClickListener {
    private String TAG = MainActivity.class.getSimpleName();
    private Button mBntInit, mBtnBurning, mBtnBanner, mBtnFull;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE","android.permission.ACTION_MANAGE_OVERLAY_PERMISSION"};
    private final int REQUEST_CODE_PERMISSIONS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                AdvertEngine.getInstance().init(MainActivity.this);
                AdvertUtils.getInstance().showLoopView(AdvertConstant.AD_SEC_FULL);
            } else {
                Uri uri = Uri.parse("package:" + MainActivity.this.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri);
                startActivityForResult(intent, 100);
            }
        }
        requestPermissions();
        setContentView(R.layout.activity_main);
        mBntInit = findViewById(R.id.btn_init);
        mBtnBurning = findViewById(R.id.btn_burning);
        mBtnBanner = findViewById(R.id.btn_banner);
        mBtnFull = findViewById(R.id.btn_full);

        mBntInit.setOnClickListener(this);
        mBtnBurning.setOnClickListener(this);
        mBtnBanner.setOnClickListener(this);
        mBtnFull.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(this)) {
                AdvertEngine.getInstance().init(MainActivity.this);
                AdvertUtils.getInstance().showLoopView(AdvertConstant.AD_SEC_FULL);
            } else {
                Toast.makeText(this,"permission denied.",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBntInit) {
            AdvertEngine.getInstance().init(MainActivity.this);
        } else if (v == mBtnBurning) {
            AdvertEngine.getInstance().burning(MainActivity.this);
        } else if (v == mBtnBanner) {
            AdvertUtils.getInstance().showLoopView(AdvertConstant.AD_SEC_BANNER);
        } else if (v == mBtnFull) {
            AdvertUtils.getInstance().showLoopView(AdvertConstant.AD_SEC_FULL);
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy()");
        AdvertEngine.getInstance().burning(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.finish();
    }

    /**
     * requestPermissions 如果不授权，数据库服务依然可以使用，仅无法保存Crash日志到SD
     */
    private void requestPermissions() {
        PermissionUtils.checkAndRequestMorePermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        Log.i(TAG, "权限已被授予");

                    }
                });
    }
}