package pri.weiqiang.tryit.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class HandlerActivity extends BaseActivity {
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mTv = findViewById(R.id.tv);
        MyHandler myHandler = new MyHandler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000000000);
        Message msg = new Message();
        msg.what = 0;
        myHandler.handleMessage(msg);


    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 0:
                    //这里可以改变activity中的UI控件的状态
                    mTv.setText("Msg = 0");
                    break;
                case 1:
                    //这里可以改变activity中的UI控件的状态
                    mTv.setText("Msg = 1");
                    break;
                /*这里可以有多条要处理信息的操作*/
                /*... ...*/
            }

        }
    }
}
