package pri.weiqiang.tryit.camerahub;

import android.app.Activity;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;

import java.io.IOException;

public class TextureViewSysCamera extends SysCamera {

    private String TAG = TextureViewSysCamera.class.getSimpleName();
    private TextureView mTextureView;
    private boolean isWideDynamicCamera;

    public TextureViewSysCamera(Activity activity, TextureView textureView, boolean isWideDynamicCamera, int cameraId) {
        this.mActivity = activity;
        this.mTextureView = textureView;
        this.mCameraId = cameraId;
        this.isWideDynamicCamera = isWideDynamicCamera;

        if (this.isWideDynamicCamera && cameraId == 2) {//前置摄像头才需要镜像处理
            final Matrix matrix = new Matrix();
            matrix.setScale(-1, 1);
            matrix.postTranslate(PREVIEW_WIDTH, 0);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextureView.setTransform(matrix);
                }
            });
        }
    }

    @Override
    public void open() {
        Log.e(TAG, "open() ");
        mCamera = Camera.open(mCameraId);
        if (isWideDynamicCamera) {
            mCamera.setDisplayOrientation(90);
        }
        startPreview();
    }

    @Override
    protected void setCameraPreview() {
        try {
            Log.e(TAG, "49");
            mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
            Log.e(TAG, "50");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
