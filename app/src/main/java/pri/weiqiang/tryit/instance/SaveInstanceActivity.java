package pri.weiqiang.tryit.instance;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class SaveInstanceActivity extends BaseActivity {

    private TextView mTvSaveInstance;
    private String TAG = SaveInstanceActivity.class.getSimpleName();
    private String oldString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_instance);
        mTvSaveInstance = findViewById(R.id.tv_save_instance);
        if (savedInstanceState != null) {
            oldString = savedInstanceState.getString("Activity");
            Log.e(TAG, "oldString:" + oldString);
            mTvSaveInstance.setText(oldString);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        String string = "activity 被系统回收了怎么办？";
        outState.putString("Activity", string);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("BaseActivity.onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            oldString = savedInstanceState.getString("Activity");
            Log.e(TAG, "oldString:" + oldString);
            mTvSaveInstance.setText(oldString);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

}
