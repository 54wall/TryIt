package pri.weiqiang.tryit.handler;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class WeakReferenceActivity extends BaseActivity {
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


}
