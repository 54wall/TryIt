package pri.weiqiang.tryit.looper;

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

public class WorkThreadActivity extends BaseActivity {
    private Button btnSendToWorkUI;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_thread);

        // 在UI线程中开启一个子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
//                    没有Looper.prepare();吧报错java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
                // 在子线程中初始化一个Looper对象
                Looper.prepare();
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        // 把UI线程发送来的消息显示到屏幕上。
                        Log.i("main", "what=" + msg.what + "," + msg.obj);
                        Toast.makeText(WorkThreadActivity.this, "what=" + msg.what + "," + msg.obj, Toast.LENGTH_SHORT).show();
                    }
                };
                // 把刚才初始化的Looper对象运行起来，循环消息队列的消息
                //没有Looper.loop();handler无法接收消息
                Looper.loop();
            }
        }).start();

        btnSendToWorkUI = findViewById(R.id.btn_send_to_work_ui);

        btnSendToWorkUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onClick方法是运行在UI线程上的
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "向子线程中发送消息！";
                // 向子线程中发送消息
                handler.sendMessage(msg);
            }
        });
    }


}
