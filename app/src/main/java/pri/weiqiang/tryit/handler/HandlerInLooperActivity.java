package pri.weiqiang.tryit.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class HandlerInLooperActivity extends BaseActivity {
    private Button mBtnSend;
    private Handler handler;
    private String TAG = HandlerInLooperActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_in_loop);
        mBtnSend = findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(7);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e(TAG, "e:" + e.toString());
                    }
                    /*
                     * java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
                     * Looper.loop()后边的代码将不会执行。
                     * 所以在while循环将失效，Looper.prepare和Looper.loop之间将处于消息队列的循环之中
                     * https://bbs.csdn.net/topics/380055233
                     * Looper.loop()之后的代码是无法运行的，因为loop()里面是个死循环，有消息就处理，没消息就挂起休眠。
                     */
                    Looper.prepare();
                    Toast.makeText(HandlerInLooperActivity.this, "Toast必须有Looper才会显示", Toast.LENGTH_LONG).show();
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Log.e(TAG, "handler msg:" + msg);
                        }
                    };
                    Looper.loop();
                    Looper.getMainLooper().quit();
                }
            }
        }).start();

    }
}
