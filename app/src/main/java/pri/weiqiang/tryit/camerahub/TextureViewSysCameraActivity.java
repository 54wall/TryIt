package pri.weiqiang.tryit.camerahub;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.permission.PermissionUtils;

public class TextureViewSysCameraActivity extends Activity {
    private final int REQUEST_CODE_PERMISSIONS = 2;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.READ_PHONE_STATE"};
    protected TextureView camera_surcaceview_l_resident;
    protected ICamera faceCameraL;
    private String TAG = TextureViewSysCameraActivity.class.getSimpleName();
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        mContext = TextureViewSysCameraActivity.this;
        setContentView(R.layout.activity_camera_hub);
        camera_surcaceview_l_resident = findViewById(R.id.camera_surfaceview_L_resident);
        faceCameraL = new TextureViewSysCamera(mContext, camera_surcaceview_l_resident, false, 0);//副屏彩色
        //open() 需要放在onResume中否则app passed NULL surface onCreate中应该是相机还没有准备好
//        faceCameraL.open();
    }

    private void requestPermissions() {
        PermissionUtils.checkAndRequestMorePermissions(TextureViewSysCameraActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        Log.e(TAG, "权限已被授予");

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
//        startPreview();
        faceCameraL.open();
    }
}
