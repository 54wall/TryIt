package pri.weiqiang.tryit.service;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * ---------------------
 * 作者：csdnYF
 * 来源：CSDN
 * 原文：https://blog.csdn.net/csdnyf/article/details/51628580
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class ServiceActivity1 extends BaseActivity {
    private static final String TAG = ServiceActivity1.class.getSimpleName();
    private MyService1 mService;
    private List<String> mData;
    private boolean mBound = false;
    private TextView textView;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: ComponentName = " + name);
            mService = ((MyService1.LocalBinder) service).getService();
            mData = ((MyService1.LocalBinder) service).getData();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        textView = findViewById(R.id.textView);
    }

    public void getData(View v) {
        if (mBound) {   //需要注意的是：绑定过程是异步的，bindService()立即返回。因此，mService和mData还有可能为null。因此需要通过mBound标志位判断下。
            String text = mService.toString() + " data : " + mData.toString();
            textView.setText(text);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService1.class);
        //发送信息给Service
        intent.putExtra("data", "msg from " + TAG);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
