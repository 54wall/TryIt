package pri.weiqiang.tryit.countdown;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * https://blog.csdn.net/u011106915/article/details/77476834
 */
public class CountDownActivity extends BaseActivity {
    private String TAG = CountDownActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        TimerTextView timerTextView = (TimerTextView) findViewById(R.id.timerTextView);
        timerTextView.setLastMillis(System.currentTimeMillis() + 2000);
        timerTextView.setContentBeforAfter("剩余", "付款");
        timerTextView.setOutOfDateText("已过期");
        timerTextView.start();

        final TimerTextView timerTextView2 = (TimerTextView) findViewById(R.id.timerTextView2);
        timerTextView2.setOnFinishListener(new TimerTextView.OnFinishListener() {
            @Override
            public void OnFinish(TimerTextView timer) {
                Log.e(TAG, "setOnFinishListener OnFinish!!!!!! ");

            }
        });
        timerTextView2.setLastMillis(System.currentTimeMillis() + 2000);
        timerTextView2.setContentBeforAfter("剩余", "抢购");
        timerTextView2.setOutOfDateText("立即抢购");
        timerTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerTextView2.isFinished()) {
                    return;
                }
                timerTextView2.setText("抢购成功");
//                Toast.makeText(CountDownActivity.this, "抢购成功", Toast.LENGTH_SHORT).show();
            }
        });
        timerTextView2.start();
    }
}
