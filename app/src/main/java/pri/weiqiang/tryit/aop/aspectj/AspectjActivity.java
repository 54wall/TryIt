package pri.weiqiang.tryit.aop.aspectj;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * Project的build.gradle需要添加下边依赖
 * dependencies {
 * classpath 'com.android.tools.build:gradle:3.4.2'
 * classpath 'org.aspectj:aspectjtools:1.8.13'
 * classpath 'org.aspectj:aspectjweaver:1.8.13'
 * }
 * Moudlede的build.gradle添加:
 * dependencies {
 * implementation 'org.aspectj:aspectjrt:1.8.13'
 * }
 */
public class AspectjActivity extends BaseActivity {
    private static final String TAG = AspectjActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectj);
    }

    /**
     * 摇一摇的模块
     *
     * @param view
     */
    @BehaviorTrace(value = "摇一摇", type = 1)
    public void mShake(View view) {
        //摇一摇的代码逻辑
        {
//            SystemClock.sleep(3000);
            Log.i(TAG, " 摇到一个红包");

        }
    }

    /**
     * 语音的模块
     *
     * @param view
     */
    @BehaviorTrace(value = "语音:", type = 1)
    public void mAudio(View view) {
        //语音代码逻辑
        {
//            SystemClock.sleep(3000);
            Log.i(TAG, "发语音：我要到一个红包啦");
        }
    }

    /**
     * 打字模块
     *
     * @param view
     */
    @BehaviorTrace(value = "打字:", type = 1)
    public void mText(View view) {
        //打字模块逻辑
        {
//            SystemClock.sleep(3000);
            Log.i(TAG, "打字逻辑，我摇到了一个大红包");

        }

    }
}
