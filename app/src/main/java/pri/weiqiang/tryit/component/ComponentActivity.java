package pri.weiqiang.tryit.component;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.constants.Constants;

/*
调用另外一个APK中指定Activity
* */
public class ComponentActivity extends BaseActivity {
    private String TAG = ComponentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
        Button mBtnInstallSilent = findViewById(R.id.btn_start_activity_install_silent);
        mBtnInstallSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityExtraInstallSilent();
            }
        });

        Button mBtnUnInSilent = findViewById(R.id.btn_start_activity_uninstall_install_silent);
        mBtnUnInSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityUnInSlient();
            }
        });
        Button mBtnUnIn = findViewById(R.id.btn_un_in);
        mBtnUnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityUnIn();
            }
        });


    }

    //静默安装隐式
    public void startActivityExtraInstallSilent() {
        ComponentName componetName = new ComponentName(
                //这个是另外一个应用程序的包名
                "pri.weiqiang.home",
                //这个参数是要启动的Activity
                "pri.weiqiang.home.ui.LauncherActivity");//DemoActivity
        Intent intent = new Intent("test");
        Bundle bundle = new Bundle();
        bundle.putString("arge1", "这是跳转过来的！来自apk1");
        bundle.putString(Constants.EXTRA_MODE, Constants.EXTRA_MODE_INSTALL_SILENT);
        bundle.putString(Constants.EXTRA_PACKAGE, "com.example.myapplication");
        bundle.putString(Constants.EXTRA_APK_NAME, "MyApplication.apk");
        bundle.putString(Constants.EXTRA_APK_PATH, Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator);
        bundle.putBoolean(Constants.EXTRA_IS_OPEN, true);
        bundle.putBoolean(Constants.EXTRA_VISIBLE, false);
        intent.putExtras(bundle);
        intent.setComponent(componetName);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Exception:" + e.toString());
            Toast.makeText(ComponentActivity.this, "Exception:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //静默卸载再静默安装 显式
    public void startActivityUnIn() {
        ComponentName componetName = new ComponentName(
                //这个是另外一个应用程序的包名
                "pri.weiqiang.home",
                //这个参数是要启动的Activity
                "pri.weiqiang.home.ui.LauncherActivity");
        Intent intent = new Intent("test");
        //我们给他添加一个参数表示从apk1传过去的
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_MODE, Constants.EXTRA_MODE_UNINSTALL_INSTALL_SILENT);
        bundle.putString(Constants.EXTRA_PACKAGE, "com.example.myapplication");
        bundle.putString(Constants.EXTRA_APK_NAME, "MyApplication.apk");
        bundle.putString(Constants.EXTRA_APK_PATH, Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator);
        bundle.putBoolean(Constants.EXTRA_VISIBLE, true);
        intent.putExtras(bundle);
        intent.setComponent(componetName);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Exception:" + e.toString());
            Toast.makeText(ComponentActivity.this, "Exception:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //静默卸载再静默安装 隐式
    public void startActivityUnInSlient() {
        ComponentName componetName = new ComponentName(
                //这个是另外一个应用程序的包名
                "pri.weiqiang.home",
                //这个参数是要启动的Activity
                "pri.weiqiang.home.ui.LauncherActivity");
        Intent intent = new Intent("test");
        //我们给他添加一个参数表示从apk1传过去的
        Bundle bundle = new Bundle();
        bundle.putString("arge1", "这是跳转过来的！来自apk1");
        bundle.putString(Constants.EXTRA_MODE, Constants.EXTRA_MODE_UNINSTALL_INSTALL_SILENT);
        bundle.putString(Constants.EXTRA_PACKAGE, "com.example.myapplication");
        bundle.putString(Constants.EXTRA_APK_NAME, "MyApplication.apk");
        bundle.putString(Constants.EXTRA_APK_PATH, Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator);
        bundle.putBoolean(Constants.EXTRA_VISIBLE, false);
        intent.putExtras(bundle);
        intent.setComponent(componetName);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Exception:" + e.toString());
            Toast.makeText(ComponentActivity.this, "Exception:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
