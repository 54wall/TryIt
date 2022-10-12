package com.afei.camerademo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * https://github.com/WuKaik/QuickCamera2
 */
public class BaseCameraActivity extends AppCompatActivity {
    public static final String TAG = BaseCameraActivity.class.getSimpleName();
    public static final int REQUEST_CODE_PREMISSION = 1;

    private TextureView textureView;
    private String mCameraId;//摄像头ID
    private Size mPreviewSize;//图像预览尺寸
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCameraSession;
    private CaptureRequest mCaptureRequest;
    private CaptureRequest.Builder mCaptureBuilder;
    private ImageReader mReader;//camera2没有预览回调，需要通过ImageReader获取数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_camera);
        mHandlerThread = new HandlerThread("QuickCamera");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        textureView = findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.d(TAG, "onSurfaceTextureAvailable: width:" + width + "height:" + height);
                //选择摄像头
                setCamera(width, height);
                //打开摄像头
                openCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    /**
     * 设置相机
     *
     * @param width
     * @param height
     */
    private void setCamera(int width, int height) {
        try {
            //遍历所有摄像头
            for (String cameraId : mCameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
                //默认打开前置摄像头
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                //根据TextureView的尺寸设置预览尺寸
                mPreviewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                Log.d(TAG, "预览Size: width:" + mPreviewSize.getWidth() + " height:" + mPreviewSize.getHeight());
                mCameraId = cameraId;
                Log.d(TAG, "摄像头Id: " + cameraId);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从摄像头支持的预览Size中选择大于并且最接近width和height的size
     *
     * @param sizeMap
     * @param width
     * @param height
     * @return
     */
    private Size getOptimalSize(Size[] sizeMap, int width, int height) {
        List<Size> sizeList = new ArrayList<>();
        for (Size option : sizeMap) {
            Log.d(TAG, "系统支持的尺寸大小==width:" + option.getWidth() + " height:" + option.getHeight());
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    sizeList.add(option);
                }
            } else {
                if (option.getWidth() > height && option.getHeight() > width) {
                    sizeList.add(option);
                }
            }
        }
        if (sizeList.size() > 0) {
            return Collections.min(sizeList, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }
            });
        }
        if (sizeList.size() > 0)
            return sizeList.get(0);
        return sizeMap[0];
    }

    /**
     * 打开摄像头
     */
    private void openCamera() {
        //检查权限
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(BaseCameraActivity.this,"must add camera premission",Toast.LENGTH_SHORT).show();
                //运行时权限
                ActivityCompat.requestPermissions(BaseCameraActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PREMISSION);
                return;
            }
            //打开相机，第一个参数指示打开哪个摄像头，第二个参数stateCallback为相机的状态回调接口，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            mCameraManager.openCamera(mCameraId, mDeviceStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 摄像头打开状态监听回调接口对象
     */
    private CameraDevice.StateCallback mDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.d(TAG, "CameraDevice.StateCallback onOpened: ");
            mCameraDevice = camera;
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.d(TAG, "CameraDevice.StateCallback onDisconnected: ");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.d(TAG, "CameraDevice.StateCallback onError: ");
        }
    };

    /**
     * 开始预览
     * 使用TextureView显示相机预览数据
     * Camera2的预览和拍照数据都是使用CameraCaptureSession会话来请求的
     */
    private void startPreview() {
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        //设置TextureView缓冲区大小
        surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        //获取Surface显示数据
        Surface surface = new Surface(surfaceTexture);
        try {
            //设置捕获请求为预览（还有其它，如拍照、录像）
            mCaptureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //可以通过这个set(key,value)方法，设置曝光啊，自动聚焦等参数！！
            //mCaptureBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            //获取ImageReader(ImageFormat不要使用jpeg,预览会出现卡顿)
            mReader = ImageReader.newInstance(mPreviewSize.getWidth(), mPreviewSize.getHeight(), ImageFormat.YUV_420_888, 2);
            //设置有图像数据流时监听
            mReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {

                    //需要调用acquireLatestImage()和close(),不然会卡顿
                    Image image = reader.acquireLatestImage();
                    //将这帧数据转成字节数组，类似于Camera1的PreviewCallback回调的预览帧数据
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    byte[] data = new byte[buffer.remaining()];
                    Log.e(TAG,"218 onImageAvailable");
                    buffer.get(data);
                    //Log.d(TAG, "onImageAvailable: data size"+data.length);
                    image.close();
                }
            }, mHandler);
            //设置预览界面为数据的显示
            mCaptureBuilder.addTarget(surface);
            mCaptureBuilder.addTarget(mReader.getSurface());
            //创建相机捕获会话
            mCameraDevice.createCaptureSession(Arrays.asList(surface, mReader.getSurface()), mCaptureStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraCaptureSession.StateCallback mCaptureStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            //创建捕获请求
            mCaptureRequest = mCaptureBuilder.build();
            mCameraSession = session;
            try {
                //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                mCameraSession.setRepeatingRequest(mCaptureRequest, null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        //退出时记得关闭
        if (mCameraSession != null) {
            mCameraSession.close();
            mCameraSession = null;
        }

        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }

        if (mReader != null) {
            mReader.close();
            mReader = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PREMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }
}
