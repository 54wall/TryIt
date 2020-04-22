package pri.weiqiang.tryit.screensaver;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.tryit.R;

public class AdActivity extends AppCompatActivity {
    private String TAG = AdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时取消定时
                Log.e(TAG, "ACTION_DOWN");
                finish();
                break;
            case MotionEvent.ACTION_UP:
                //抬起时启动定时
                Log.e(TAG, "ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
