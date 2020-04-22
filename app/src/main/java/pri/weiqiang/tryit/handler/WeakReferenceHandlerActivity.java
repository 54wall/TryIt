package pri.weiqiang.tryit.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class WeakReferenceHandlerActivity extends BaseActivity {
    TextView mTvHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weakreference_handler);
        mTvHandler = findViewById(R.id.tv_handler);
        MyHandler testHandler = new MyHandler(this);
        testHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000000000);
        Message msg = new Message();
        msg.what = 1;
        testHandler.handleMessage(msg);
    }

    /*去掉 static 依然会报错his Handler class should be static or leaks might occur (*/
    static class MyHandler extends Handler {
        //注意下面的“WeakReferenceHandlerActivity”类是MyHandler类所在的外部类，即所在的activity
        WeakReference<WeakReferenceHandlerActivity> mActivity;

        MyHandler(WeakReferenceHandlerActivity activity) {
            mActivity = new WeakReference<WeakReferenceHandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WeakReferenceHandlerActivity theActivity = mActivity.get();
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 0:
                    //这里可以改变activity中的UI控件的状态
                    theActivity.mTvHandler.setText("Msg = 0");
                    break;
                case 1:
                    //这里可以改变activity中的UI控件的状态
                    theActivity.mTvHandler.setText("Msg = 1");
                    break;
                /*这里可以有多条要处理信息的操作*/
                /*... ...*/
            }

        }
    }
}
