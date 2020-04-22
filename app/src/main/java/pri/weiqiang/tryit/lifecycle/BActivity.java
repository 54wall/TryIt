package pri.weiqiang.tryit.lifecycle;

import android.os.Bundle;
import android.util.Log;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class BActivity extends BaseActivity {

    private String TAG = BActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "B onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "B onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "B onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "B onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "B onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "B onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "B onDestroy");
        super.onDestroy();
    }
}
