package pri.weiqiang.tryit.camerahub;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * @author durui
 * @date 2019/4/25
 * @description
 */
public class SysCamera implements ICamera {
    public static final String TAG = SysCamera.class.getSimpleName();
    //不是640*480直接报错
    public static final int PREVIEW_WIDTH = 640;
    public static final int PREVIEW_HEIGHT = 480;

    protected PreviewCallback mPreviewCallback;
    protected Activity mActivity;
    protected Camera mCamera = null;
    protected int mCameraId;
    private SurfaceHolder mSurfaceHodler;
    private SurfaceView mSurfaceView;
    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
//            open();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
//            close();
        }
    };

    public SysCamera() {

    }

    public SysCamera(Activity activity, SurfaceView surfaceView, int cameraId) {
        this.mActivity = activity;
        this.mSurfaceView = surfaceView;
        this.mCameraId = cameraId;
        this.mSurfaceHodler = surfaceView.getHolder();
        mSurfaceHodler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHodler.addCallback(mCallback);
    }

    @Override
    public void setPreviewCallback(ICamera.PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
    }

    @Override
    public void open() {
        mCamera = Camera.open(mCameraId);
        startPreview();
    }

    protected void setCameraPreview() {
        try {
            Log.e(TAG, "setCameraPreview 71");
            mCamera.setPreviewDisplay(mSurfaceHodler);
            Log.e(TAG, "setCameraPreview 73");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "setCameraPreview 76");
        }
    }

    @Override
    public void startPreview() {
        Log.e(TAG, "startPreview()");
        // 启动摄像头预览
        if (mCamera != null) {
            Log.e(TAG, "83");
            setCameraPreview();
            Log.e(TAG, "85");
            setCameraParams(mCamera);
            Log.e(TAG, "87");
            mCamera.startPreview();
            Log.e(TAG, "89");
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    Log.e(TAG, "93 data:" + data);
//                    mPreviewCallback.onPreviewFrame(data, PREVIEW_WIDTH, PREVIEW_HEIGHT);
                }
            });
        }
    }

    @Override
    public void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    public void close() {
        try {
            Log.i("SysCamera", "isLive close(), mCamera = " + mCamera);
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void setCameraParams(Camera camera) {


//        Camera.Parameters parameters = mCamera.getParameters();
//        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
//        int[] a = new int[sizes.size()];
//        int[] b = new int[sizes.size()];
//        for (int i = 0; i < sizes.size(); i++) {
//            int supportH = sizes.get(i).height;
//            int supportW = sizes.get(i).width;
//            a[i] = Math.abs(supportW - 1280);
//            b[i] = Math.abs(supportH - 720);
//            android.util.Log.e(TAG,"supportW:"+supportW+"supportH:"+supportH);
//        }
//        int minW=0,minA=a[0];
//        for( int i=0; i<a.length; i++){
//            if(a[i]<=minA){
//                minW=i;
//                minA=a[i];
//            }
//        }
//        int minH=0,minB=b[0];
//        for( int i=0; i<b.length; i++){
//            if(b[i]<minB){
//                minH=i;
//                minB=b[i];
//            }
//        }
//        android.util.Log.e(TAG,"result="+sizes.get(minW).width+"x"+sizes.get(minH).height);
//
//        List<Integer> list = parameters.getSupportedPreviewFrameRates();
//        parameters.setPreviewSize(sizes.get(minW).width,sizes.get(minH).height); // 设置预览图像大小
//        parameters.setPreviewFrameRate(list.get(list.size() - 1));


        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);
        parameters.setPictureSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        parameters.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        parameters.setJpegQuality(100); // 设置照片质量
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }
        camera.cancelAutoFocus();//自动对焦。
        camera.setParameters(parameters);
    }
}
