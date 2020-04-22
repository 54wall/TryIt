package pri.weiqiang.tryit.screensaver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Author:
 * Date: 2017/8/10
 * Description:
 * ---------------------
 * 版权声明：本文为CSDN博主「yaun封不动」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/Yuan_feng_bu_dong/article/details/77036440
 */

public abstract class ScreenSaverBaseActivity extends AppCompatActivity {
    public CountDownTimer countDownTimer;
    public Context context;
    private String TAG = ScreenSaverBaseActivity.class.getSimpleName();
    private long advertisingTime = 15 * 1000;//定时跳转广告时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置屏幕长亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = this;
        setContentView(getLayoutRes());

    }

    protected abstract int getLayoutRes();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时取消定时
                Log.e(TAG, "取消定时 ACTION_DOWN");
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "startAD ACTION_UP");
                //抬起时启动定时
                startAD();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 跳轉廣告
     */
    public void startAD() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(advertisingTime, 1000l) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    //定时完成后的操作
                    //跳转到广告页面
                    startActivity(new Intent(context, AdActivity.class));
                    Log.e(TAG, "");
                }
            };
            countDownTimer.start();
        } else {
            countDownTimer.start();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //显示是启动定时
        startAD();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当activity不在前台是停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁时停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

