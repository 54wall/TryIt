package com.zhouzi.aidlservice_master;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zhouzi.aidlclient_master.aidl.SsoAuth;

public class ZhouZiSsoAuthService extends Service {

    ZhouZiSsoAuthImpl zhouZiSsoAuth = new ZhouZiSsoAuthImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return zhouZiSsoAuth;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("AIDL", "###sso auth created");
    }

    class ZhouZiSsoAuthImpl extends SsoAuth.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void ssoAuth(String userNanme, String pwd) throws RemoteException {
            Log.e("AIDL", "这里是ZhouZi客户端，执行SSO登录啦");
            Log.e("AIDL", "userName:" + userNanme);
            Log.e("AIDL", "pwd:" + pwd);
        }
    }

}
