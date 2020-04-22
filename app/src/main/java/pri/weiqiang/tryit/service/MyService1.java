package pri.weiqiang.tryit.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyService1 extends Service {
    private static final String TAG = MyService1.class.getSimpleName();
    private final IBinder localBinder = new LocalBinder();
    private List<String> data = new ArrayList<>();

    public MyService1() {
        data.add("This is some msg from " + TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        String s = intent.getStringExtra("data");
        Log.i(TAG, "onBind: 接收 " + s);
        data.add(s);
        return localBinder;
    }

    public class LocalBinder extends Binder {
        MyService1 getService() {
            return MyService1.this;
        }

        List<String> getData() {
            return data;
        }
    }
}

