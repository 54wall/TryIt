package com.wildma.mqttandroidclient;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wildma.mqttandroidclient.permission.DialogHelper;
import com.wildma.mqttandroidclient.permission.PermissionConstants;
import com.wildma.mqttandroidclient.permission.PermissionUtils;
import com.wildma.mqttandroidclient.util.NetUtils;
import com.wildma.mqttandroidclient.util.SystemInfoUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        final TextView mTvIp = findViewById(R.id.tv_ip);
        new Thread(new Runnable() {
            @Override
            public void run() {
               String[] s =  SystemInfoUtil.getIpMacAddress(MainActivity.this);
                if(s!=null){
                    Log.e(TAG,"getIpMacAddress"+s[0]);
                }else {
                    Log.e(TAG,"s==null");
                }



            }
        }).start();

        Button mBtnGetIp = findViewById(R.id.btn_get_ip);
        mBtnGetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SystemInfoUtil.getIpMacAddress(MainActivity.this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG,"NetUtils.getLocalInetAddress().getHostAddress()"+NetUtils.getLocalInetAddress().getHostAddress());
                        Log.e(TAG,"NetUtils.getLocalInetAddress().getHostName()"+NetUtils.getLocalInetAddress().getHostName());
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvIp.setText(NetUtils.getLocalInetAddress().getHostAddress());
                            }
                        });
                    }
                }).start();


            }
        });
    }

    public void publish(View view) {
        //模拟闸机设备发送消息过来
        MyMqttService.publish("tourist enter");
    }

    /**
     * 申请权限
     */
    public void requestPermission() {
        PermissionUtils.permission(PermissionConstants.PHONE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        Log.d(TAG, "onDenied: 权限被拒绝后弹框提示");
                        DialogHelper.showRationaleDialog(shouldRequest, MainActivity.this);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        mIntent = new Intent(MainActivity.this, MyMqttService.class);
                        //开启服务

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(mIntent);
                        } else {
                            startService(mIntent);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        Log.d(TAG, "onDenied: 权限被拒绝");
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog(MainActivity.this);
                        }
                    }
                })
                .request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止服务
        stopService(mIntent);
    }
}
