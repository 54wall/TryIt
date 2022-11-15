package pri.weiqiang.tryit.ota.update;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static String updatePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/update.zip";
    private Button mCancle;
    private Button mConfirm;
    //simple_test_code
    private static File RECOVERY_DIR = new File("/cache/recovery");
    private static File COMMAND_FILE = new File(RECOVERY_DIR, "command");
    private static void recoveryMode(Context context) throws IOException {
        String arg = "–update_package=/sdcard/update.zip";
        RECOVERY_DIR.mkdirs();
        FileWriter command = new FileWriter(COMMAND_FILE);
        try {
            command.write(arg); // 往/cache/recovery/command中写入recoveryELF的执行参数。
            command.write("\n");
        } finally {
            command.close();
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        pm.reboot("recovery"); // 调用PowerManager类中的reboot方法
        throw new IOException("Reboot failed (no permissions?)");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCancle = (Button) findViewById(R.id.cancle);
        mCancle.setOnClickListener(this);
        mConfirm = (Button) findViewById(R.id.confirm);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                finish();
                break;
            case R.id.confirm:
                confirmUpdatePackageExist();
                break;
            default:
                break;
        }
    }
    public void confirmUpdatePackageExist() {
        File updatePackage = new File(updatePath);
        if (isFileExist(updatePackage)) {
            boolean isZipfileExist = updatePackage.exists();
            if (!isZipfileExist) {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.update_package_not_found), Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 100);
                toast.show();
            } else {
                Intent intent = new Intent();
                intent.setClass(this, OtaIntroductionActivity.class);
                startActivity(intent);
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.ota_not_mounted), Toast.LENGTH_LONG);
            toast.setGravity(0, 0, 100);
            toast.show();
        }
    }
    public boolean isFileExist(File file) {
        if (file.exists()) {
            return true;
        } else {
            Log.d(TAG, "Update zip file not exist.");
            return false;
        }
    }
}