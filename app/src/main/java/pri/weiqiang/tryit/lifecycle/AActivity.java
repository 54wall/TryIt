package pri.weiqiang.tryit.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class AActivity extends BaseActivity {

    private String TAG = AActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "A onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Button mBtnLifeCycle = findViewById(R.id.btn_b);
        mBtnLifeCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(AActivity.this, BActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onStart() {
        Log.e(TAG, "A onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "A onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "A onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "A onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "A onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "A onDestroy");
        super.onDestroy();
    }
}
