package pri.weiqiang.study.LogcatViewer;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logcatService(View view) {
        Log.e(TAG, "LogcatService");
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.weijiaxing.logcatviewer", "com.weijiaxing.logviewer.LogcatService"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    public void start(View view) {
        Log.e(TAG, "LogcatService");
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.weijiaxing.logcatviewer", "com.weijiaxing.logviewer.LogcatService"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    public void stop(View view) {
        Log.e(TAG, "LogcatService");
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.weijiaxing.logcatviewer", "com.weijiaxing.logviewer.LogcatService"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }
}