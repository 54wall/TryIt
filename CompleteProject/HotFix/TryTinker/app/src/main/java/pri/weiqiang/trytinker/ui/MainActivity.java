package pri.weiqiang.trytinker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

import pri.weiqiang.trytinker.R;
import pri.weiqiang.trytinker.tinker.TinkerManager;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private static final String FILE_END = ".apk";
    private String mPatchDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatchDir = getExternalCacheDir().getAbsoluteFile() + "/tpatch/";
        Log.e(TAG,"mPatchDir:"+mPatchDir);
        //为了创建我们的文件夹
        File file = new File(mPatchDir);
        if (file == null || file.exists()) {
            file.mkdir();
        }
    }

    public void loadPatch(View view) {
        TinkerManager.loadPatch(getPatchName());
    }

    //构造patch 文件名
    private String getPatchName() {
        return mPatchDir.concat("update").concat(FILE_END);
    }
}