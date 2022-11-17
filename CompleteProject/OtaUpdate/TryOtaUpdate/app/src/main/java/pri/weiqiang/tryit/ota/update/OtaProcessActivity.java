package pri.weiqiang.tryit.ota.update;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class OtaProcessActivity extends Activity implements RecoverySystem.ProgressListener {
    private static final String TAG = "OtaProcessActivity";
    private static final int VERIFY_COMPLETE = 70;
    private static final int INSTALL_COMPLETE = 100;
    private static String updatePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/update.zip";
    private ProgressBar mProcessbar;
    private TextView mUpdateStep;
    private TextView mUpdateState;
    private TextView mNotify;
    private ImageView mCompleteImg;
    Thread runnable = new Thread() {
        @Override
        public void run() {
            Log.d(TAG, "Start update .............");
            File file = new File(updatePath);
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            assert pm != null;
            /*SCREEN_BRIGHT_WAKE_LOCK' is deprecated as of API 15: Android 4.0.3 */
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "demo:needProcessBar");
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "demo:needProcessBar");
            try {
                wl.acquire(10 * 60 * 1000L /*10 minutes*/);//升级保持亮屏状态
                Log.d(TAG, "Verify package start before.");
                RecoverySystem.verifyPackage(file, OtaProcessActivity.this, null);
                Log.d(TAG, "Verify package complete.");
                RecoverySystem.installPackage(OtaProcessActivity.this, file);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            } finally {
                wl.release();
            }
        }
    };
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            mProcessbar.setProgress(msg.arg1);
            switch (msg.arg1) {
                case VERIFY_COMPLETE:
                    mUpdateStep.setText(getString(R.string.the_next_step_number));
                    mUpdateState.setText(getString(R.string.install_process));
                    mNotify.setText("");
                    break;
                case INSTALL_COMPLETE:
                    mUpdateState.setText(getString(R.string.install_process_complete));
                    mNotify.setText(getString(R.string.restart));
//                    mCompleteImg.setBackgroundResource(R.drawable.ota_complete);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ota_process);
        mUpdateStep = (TextView) findViewById(R.id.step_number);
        mUpdateState = (TextView) findViewById(R.id.processbar_title);
        mNotify = (TextView) findViewById(R.id.update_notify);
        mCompleteImg = (ImageView) findViewById(R.id.update_complete);
        mProcessbar = (ProgressBar) findViewById(R.id.processbar);
        mProcessbar.setMax(110);
        mProcessbar.setProgress(0);
        mProcessbar.setIndeterminate(false);
        runnable.start();
    }

    @Override
    public void onProgress(int progress) {
        Log.d(TAG, "progress=" + progress);
        Message msg = Message.obtain();
        msg.arg1 = progress;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onBackPressed() {
// TODO Auto-generated method stub
        System.exit(0);
    }
}