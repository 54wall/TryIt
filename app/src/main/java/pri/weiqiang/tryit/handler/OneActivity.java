package pri.weiqiang.tryit.handler;

import android.os.Bundle;
import android.os.Handler;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * https://blog.csdn.net/Double2hao/article/details/78784758
 */
public class OneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000000000);
    }
}
