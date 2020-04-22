package pri.weiqiang.tryit.aop.hujiang;

import android.os.Bundle;
import android.util.Log;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/*沪江aspectj可以再沪江的依赖完全屏蔽下使用，即只有原生Aspectj依赖和配置即可运行
 * 这个特别和获取一个类全部方法的各个执行顺序和计算执行时间
 * */
public class PerformanceActivity extends BaseActivity {
    private String TAG = PerformanceActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        initA();
        initB();
        initC();
    }

    private void initA() {

        Log.e(TAG, "initA");//AOP 可以实现输出initA()方法名
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initB() {
        Log.e(TAG, "initB");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initC() {
        Log.e(TAG, "initC");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
