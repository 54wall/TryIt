package pri.weiqiang.tryit.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyService2 extends Service {
    private static final String TAG = MyService2.class.getSimpleName();
    private final IBinder localBinder = new LocalBinder();
    private List<String> data = new ArrayList<>();
    private MissionCompleteListener listener;

    public MyService2() {
        data.add("This is some msg from " + TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        String s = intent.getStringExtra("data");
        Log.i(TAG, "onBind: 接收 " + s);
        data.add(s);
        return localBinder;
    }

    public MissionCompleteListener getListener() {
        return listener;
    }

    public void setListener(MissionCompleteListener listener) {
        this.listener = listener;
    }

    //Service中添加
    public interface MissionCompleteListener {
        void onComplete();
    }

    public class LocalBinder extends Binder {
        MyService2 getService() {
            return MyService2.this;
        }

        List<String> getData() {
            return data;
        }
    }

    private class MyAsyncTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            listener.onComplete();
        }
    }

}

