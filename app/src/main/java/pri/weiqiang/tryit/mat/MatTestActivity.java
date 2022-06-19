package pri.weiqiang.tryit.mat;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

//        https://blog.51cto.com/u_15328720/3403613
public class MatTestActivity extends BaseActivity {
    private String TAG = MatTestActivity.class.getSimpleName();
    private final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_test);
        Log.e(TAG,"mHandler:"+mHandler.toString());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "------postDelayed------");
            }
        }, 800000L);

    }

}
