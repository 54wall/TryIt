package pri.weiqiang.tryit.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class KillThreadActivity extends BaseActivity {

    String TAG = KillThreadActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_thread);
        Button mBtnDoNothing = findViewById(R.id.btn_do_nothing);
        mBtnDoNothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunnerDoNothing runnerDoNothing = new RunnerDoNothing();
                Thread thread = new Thread(runnerDoNothing);
                thread.start();
            }
        });
        Button mBtnStopSign = findViewById(R.id.btn_stop_sign);
        mBtnStopSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button mBtnInterrupt = findViewById(R.id.btn_interrupt);
        mBtnInterrupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    private class RunnerDoNothing implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
        public void run() {
            for (int i = 0; i < 1000; i++) {
                Log.e(TAG, "RunnerDoNothing——————————" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class RunnerStopSign implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程

        private volatile boolean isCancelled;

        public void run() {
            while (!isCancelled) {
                for (int i = 0; i < 1000; i++) {
                    Log.e(TAG, "RunnerStopSign——————————" + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void cancel() {
            isCancelled = true;
        }
    }
}
