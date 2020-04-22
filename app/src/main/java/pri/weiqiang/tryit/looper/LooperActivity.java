package pri.weiqiang.tryit.looper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

//https://www.cnblogs.com/plokmju/p/android_Looper.html
public class LooperActivity extends BaseActivity {
    private static MyHandler handler;
    private Button mBtnToUI1, mBtnToUI2;
    private TextView mTvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        mTvMsg = findViewById(R.id.tv_msg);
        mBtnToUI1 = findViewById(R.id.btn_to_ui_1);
        mBtnToUI2 = findViewById(R.id.btn_to_ui_2);

        mBtnToUI1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用Activity内部的Looper对象
                handler = new MyHandler();
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "默认Looper";
                handler.sendMessage(msg);
            }
        });
        mBtnToUI2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前线程的Looper
                Looper looper = Looper.myLooper();
                handler = new MyHandler(looper);
                Message msg = Message.obtain();
                msg.what = 2;
                msg.obj = "使用当前线程的Looper";
                handler.sendMessage(msg);
            }
        });
        Button mBtnWworkThreadActivity = findViewById(R.id.btn_work_thread_activity);
        mBtnWworkThreadActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(LooperActivity.this, WorkThreadActivity.class);
                startActivity(i);
            }
        });
    }

    class MyHandler extends Handler {

        MyHandler() {
        }

        MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTvMsg.setText("what=" + msg.what + "," + msg.obj);
        }
    }
}