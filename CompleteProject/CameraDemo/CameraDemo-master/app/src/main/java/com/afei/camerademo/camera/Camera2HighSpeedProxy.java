package com.afei.camerademo.camera;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Camera2HighSpeedProxy {

    private static final String TAG = Camera2HighSpeedProxy.class.getSimpleName();

    private Activity mActivity;

    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT; // 要打开的摄像头ID
    private Size mPreviewSize; // 预览大小
    private CameraManager mCameraManager; // 相机管理者
    private CameraCharacteristics mCameraCharacteristics; // 相机属性
    private CameraDevice mCameraDevice; // 相机对象
    private CameraConstrainedHighSpeedCaptureSession mCaptureSession;
    private CaptureRequest.Builder mPreviewRequestBuilder; // 相机预览请求的构造器
    private CaptureRequest mPreviewRequest;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private ImageReader mImageReaderTakePicture;
    private ImageReader mImageReaderByte;//camera2没有预览回调，需要通过ImageReader获取数据
    private Surface mPreviewSurface;
    private SurfaceTexture mPreviewSurfaceTexture;
    private OrientationEventListener mOrientationEventListener;

    private int mDisplayRotate = 0;
    private int mDeviceOrientation = 0; // 设备方向，由相机传感器获取
    private int mZoom = 1; // 缩放


    private Size mVideoSize;
    private int mSreenRotation;
    //屏幕方向
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static int SCREEN_ORIENTATION_0 = 0;
    private static int SCREEN_ORIENTATION_90 = 1;
    private static int SCREEN_ORIENTATION_180 = 2;
    private static int SCREEN_ORIENTATION_270 = 3;
    static {
        ORIENTATIONS.append(SCREEN_ORIENTATION_0, 90);
        ORIENTATIONS.append(SCREEN_ORIENTATION_90, 0);
        ORIENTATIONS.append(SCREEN_ORIENTATION_180, 270);
        ORIENTATIONS.append(SCREEN_ORIENTATION_270, 180);
    }
    private MediaRecorder mMediaRecorder = null;
    private boolean mIsRecordingVideo = false;

    /**
     * 打开摄像头的回调
     */
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.d(TAG, "onOpened");
            mCameraDevice = camera;
            initPreviewRequest();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.d(TAG, "onDisconnected");
            releaseCamera();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.e(TAG, "Camera Open failed, error: " + error);
            releaseCamera();
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    public Camera2HighSpeedProxy(Activity activity) {
        mActivity = activity;
        mCameraManager = (CameraManager) mActivity.getSystemService(Context.CAMERA_SERVICE);
        mOrientationEventListener = new OrientationEventListener(mActivity) {
            @Override
            public void onOrientationChanged(int orientation) {
                mDeviceOrientation = orientation;
            }
        };
    }
    private CustomSize customVideoSize;
    @SuppressLint("MissingPermission")
    public void openCamera(int width, int height) {
        Log.e(TAG, "openCamera");
        startBackgroundThread(); // 对应 releaseCamera() 方法中的 stopBackgroundThread()
        mOrientationEventListener.enable();
        try {
            mCameraCharacteristics = mCameraManager.getCameraCharacteristics(Integer.toString(mCameraId));
            StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics
                    .SCALER_STREAM_CONFIGURATION_MAP);
            for (Range<Integer> fpsRange : map.getHighSpeedVideoFpsRanges()) {
                Log.d(TAG, "openCamera: [width, height] = "+ fpsRange.toString());
            }

            List<CustomSize> highSpeedSizes = new ArrayList<>();

            for (Range<Integer> fpsRange : map.getHighSpeedVideoFpsRanges()) {
                if (fpsRange.getLower().equals(fpsRange.getUpper())) {
                    for (Size size : map.getHighSpeedVideoSizesFor(fpsRange)) {
                        CustomSize videoSize = new CustomSize(size.getWidth(), size.getHeight());
                        if (videoSize.hasHighSpeedCamcorder(CameraMetadata.LENS_FACING_FRONT)) {
                            videoSize.setFps(fpsRange.getUpper());
                            Log.d(TAG, "Support HighSpeed video recording for " + videoSize.toString());
                            highSpeedSizes.add(videoSize);
                        }
                    }
                }
            }

            if (highSpeedSizes.isEmpty()) {
                Log.e(TAG,"highSpeedSizes.isEmpty()");
            }

            Collections.sort(highSpeedSizes);
            customVideoSize = highSpeedSizes.get(highSpeedSizes.size() - 1);
            /**
             * java.lang.IllegalArgumentException: Surface size 1080x1920 is not part of the high speed supported size list [1280x720, 1920x1080]
             */
            Size size = new Size(highSpeedSizes.get(highSpeedSizes.size() - 1).getWidth(),
                    highSpeedSizes.get(highSpeedSizes.size() - 1).getHeight());
            Log.e(TAG,"……………………getHeight:"+size.getHeight()+",……………………getWidth"+size.getWidth());
            mVideoSize = size;
            mPreviewSize = mVideoSize;


            // 拍照大小，选择能支持的一个最大的图片大小
            Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new
                    CompareSizesByArea());
            Log.e(TAG, "……………………picture size largest.getWidth(): " + largest.getWidth() + "*……………………largest.getHeight():" + largest.getHeight());
//            mImageReaderTakePicture = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 2);
            mImageReaderTakePicture = ImageReader.newInstance(largest.getHeight(), largest.getWidth(), ImageFormat.JPEG, 2);
            // 预览大小，根据上面选择的拍照图片的长宽比，选择一个和控件长宽差不多的大小
//            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height, largest);
            Log.e(TAG, "preview size: " + mPreviewSize.getWidth() + "*" + mPreviewSize.getHeight());
            initMediaRecorder();
            // 打开摄像头
            mCameraManager.openCamera(Integer.toString(mCameraId), mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void releaseCamera() {
        Log.v(TAG, "releaseCamera");
        if (null != mCaptureSession) {
            mCaptureSession.close();
            mCaptureSession = null;
        }
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        if (mImageReaderTakePicture != null) {
            mImageReaderTakePicture.close();
            mImageReaderTakePicture = null;
        }
        mOrientationEventListener.disable();
        stopBackgroundThread(); // 对应 openCamera() 方法中的 startBackgroundThread()
    }

    public void setImageAvailableListener(ImageReader.OnImageAvailableListener onImageAvailableListener) {
        if (mImageReaderTakePicture == null) {
            Log.w(TAG, "setImageAvailableListener: mImageReader is null");
            return;
        }
        mImageReaderTakePicture.setOnImageAvailableListener(onImageAvailableListener, null);
    }

    public void setPreviewSurface(SurfaceHolder holder) {
        mPreviewSurface = holder.getSurface();
    }

    public void setPreviewSurface(SurfaceTexture surfaceTexture) {
        mPreviewSurfaceTexture = surfaceTexture;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPreviewRequest() {
        try {
            closePreviewSession();
            //设置捕获请求为预览（还有其它，如拍照、录像）
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //可以通过这个set(key,value)方法，设置曝光啊，自动聚焦等参数！！
            //mCaptureBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            if (mPreviewSurfaceTexture != null && mPreviewSurface == null) { // use texture view
                mPreviewSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
                mPreviewSurface = new Surface(mPreviewSurfaceTexture);
            }
            Log.e(TAG,"mPreviewSize.getWidth():"+mPreviewSize.getWidth()+",mPreviewSize.getHeight():"+mPreviewSize.getHeight());
            //获取ImageReader(ImageFormat不要使用jpeg,预览会出现卡顿)
            //通过mReader设置图像数据分辨率，可以与预览分辨率不同
//            mImageReaderByte = ImageReader.newInstance(640, 480, ImageFormat.YUV_420_888, 2);
            mImageReaderByte = ImageReader.newInstance(mPreviewSize.getWidth(), mPreviewSize.getHeight(), ImageFormat.YUV_420_888, 2);
            //设置有图像数据流时监听
            mImageReaderByte.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
//                    Log.e(TAG,"onImageAvailable");
                    //需要调用acquireLatestImage()和close(),不然会卡顿
                    Image image = reader.acquireLatestImage();

                    if(image!=null){
//                        Log.e(TAG,"image.getHeight():"+image.getHeight());
                        //将这帧数据转成字节数组，类似于Camera1的PreviewCallback回调的预览帧数据
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        //Log.d(TAG, "onImageAvailable: data size"+data.length);
                        image.close();
                    } else {
                        Log.e(TAG, "image==null");
                    }

                }
            }, mBackgroundHandler);
            //设置预览界面为数据的显示,增加mReader获取
            mPreviewRequestBuilder.addTarget(mPreviewSurface); // 设置预览输出的 Surface
//            mPreviewRequestBuilder.addTarget(mImageReaderByte.getSurface());
            /**
             * 报错：2022-10-12 14:47:37.300 16455-16503/com.afei.camerademo E/AndroidRuntime: FATAL EXCEPTION: CameraBackground
             *     Process: com.afei.camerademo, PID: 16455
             *     java.lang.IllegalArgumentException: CaptureRequest contains unconfigured Input/Output Surface!
             *     Arrays.asList(mPreviewSurface, mImageReader.getSurface()),中添加mReader.getSurface()
             */
            /**
             * Output surface list must not be null and the size must be no more than 2
             */
//            mPreviewSurface.
            mCameraDevice.createConstrainedHighSpeedCaptureSession(Arrays.asList(/*mImageReaderByte.getSurface(),*/ mPreviewSurface/*, mImageReaderTakePicture.getSurface()*/),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            mCaptureSession =(CameraConstrainedHighSpeedCaptureSession) session;
                            // 设置连续自动对焦
                            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest
                                    .CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                            // 设置自动曝光
                            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest
                                    .CONTROL_AE_MODE_ON_AUTO_FLASH);
                            // 设置完后自动开始预览
                            mPreviewRequest = mPreviewRequestBuilder.build();
                            startPreview();
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                            Log.e(TAG, "ConfigureFailed. session: mCaptureSession");
                        }
                    }, mBackgroundHandler); // handle 传入 null 表示使用当前线程的 Looper
        } catch (Exception e) {
            /**
             * CameraAccessException更换为Exception就不再崩溃了，也没有这个错误，但是没有预览，但可以拍摄高速视频
             */
            e.printStackTrace();
        }
    }

    public void startPreview() {
        Log.v(TAG, "startPreview");
        if (mCaptureSession == null || mPreviewRequestBuilder == null) {
            Log.w(TAG, "startPreview: mCaptureSession or mPreviewRequestBuilder is null");
            return;
        }
        try {
            // 开始预览，即一直发送预览的请求
//            mCaptureSession.setRepeatingRequest(mPreviewRequest, null, mBackgroundHandler);

            setUpCaptureRequestBuilder(mPreviewRequestBuilder);
            List<CaptureRequest> mPreviewBuilderBurst = mCaptureSession.createHighSpeedRequestList(mPreviewRequestBuilder.build());
            mCaptureSession.setRepeatingBurst(mPreviewBuilderBurst, null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the camera preview. {@link #startPreview()} needs to be called in advance.
     */
    private void updatePreviewHighSpeed() {
        if (null == mCameraDevice) {
            return;
        }
        try {
            HandlerThread thread = new HandlerThread("CameraHighSpeedPreview");
            thread.start();


            setUpCaptureRequestBuilder(mPreviewRequestBuilder);
            List<CaptureRequest> mPreviewBuilderBurst = mCaptureSession.createHighSpeedRequestList(mPreviewRequestBuilder.build());
            mCaptureSession.setRepeatingBurst(mPreviewBuilderBurst, null, mBackgroundHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
        int fps = customVideoSize.getFps();
        Range<Integer> fpsRange = Range.create(fps, fps);
        builder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fpsRange);
    }

    public void stopPreview() {
        Log.v(TAG, "stopPreview");
        if (mCaptureSession == null || mPreviewRequestBuilder == null) {
            Log.w(TAG, "stopPreview: mCaptureSession or mPreviewRequestBuilder is null");
            return;
        }
        try {
            mCaptureSession.stopRepeating();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void captureStillPicture() {
        try {
            CaptureRequest.Builder captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice
                    .TEMPLATE_STILL_CAPTURE);
//            captureBuilder.addTarget(mImageReaderTakePicture.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(mDeviceOrientation));
            // 预览如果有放大，拍照的时候也应该保存相同的缩放
            Rect zoomRect = mPreviewRequestBuilder.get(CaptureRequest.SCALER_CROP_REGION);
            if (zoomRect != null) {
                captureBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoomRect);
            }
            mCaptureSession.stopRepeating();
            mCaptureSession.abortCaptures();
            final long time = System.currentTimeMillis();
            mCaptureSession.capture(captureBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                               @NonNull CaptureRequest request,
                                               @NonNull TotalCaptureResult result) {
                    Log.w(TAG, "onCaptureCompleted, time: " + (System.currentTimeMillis() - time));
                    /**
                     * 高速模式不支持拍照
                     * Constrained high speed session doesn't support this method
                     *  at android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.capture(CameraConstrainedHighSpeedCaptureSessionImpl.java:203)
                     */
                    try {
                        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata
                                .CONTROL_AF_TRIGGER_CANCEL);
                        mCaptureSession.capture(mPreviewRequestBuilder.build(), null, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    startPreview();
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private int getJpegOrientation(int deviceOrientation) {
        if (deviceOrientation == OrientationEventListener.ORIENTATION_UNKNOWN)
            return 0;
        int sensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;
        // Reverse device orientation for front-facing cameras
        boolean facingFront = mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics
                .LENS_FACING_FRONT;
        if (facingFront) deviceOrientation = -deviceOrientation;
        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        int jpegOrientation = (sensorOrientation + deviceOrientation + 360) % 360;
        Log.d(TAG, "jpegOrientation: " + jpegOrientation);
        return jpegOrientation;
    }


    CamcorderProfile profile;

    private void initMediaRecorder() {

        //获取摄像头支持的配置属性
        StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        //获取最佳的录像尺寸
//        mVideoSize = getVideoSize(map.getOutputSizes(MediaRecorder.class));
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
             profile = customVideoSize.getCamcorderProfile();
        }

    }

    public boolean isRecordingVideo() {
        return mIsRecordingVideo;
    }

    /**
     * 开始录像
     */
    public void startRecording() {
        prepareRecording();
        mMediaRecorder.start();
        mIsRecordingVideo = true;
    }

    /**
     * 停止录像
     */
    public void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mIsRecordingVideo = false;
        initPreviewRequest();
        startPreview();
    }

    /**
     * 显示toast信息
     */
    private void showToast(String text) {
        if (!text.equals("")) {
            Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Recording准备工作
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void prepareRecording() {
        try {
            closePreviewSession();
            setUpMediaRecorder();
//            mPreviewSurfaceTexture.setDefaultBufferSize(1280, 720);
            mPreviewSurfaceTexture.setDefaultBufferSize(customVideoSize.getWidth(), customVideoSize.getHeight());
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO);
            List<Surface> surfaces = new ArrayList<>();
            Surface textureSurface = new Surface(mPreviewSurfaceTexture);
            surfaces.add(new Surface(mPreviewSurfaceTexture));
            mPreviewRequestBuilder.addTarget(textureSurface);
            Surface mediaSurface = mMediaRecorder.getSurface();
            surfaces.add(mediaSurface);
            mPreviewRequestBuilder.addTarget(mediaSurface);

            mCameraDevice.createConstrainedHighSpeedCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        mCaptureSession =(CameraConstrainedHighSpeedCaptureSession) session;
                        mPreviewRequest = mPreviewRequestBuilder.build();
                        startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("创建RecordRequest失败！");
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 停止预览session
     */
    private void closePreviewSession() {
        if (mCaptureSession != null) {
            mCaptureSession.close();
            mCaptureSession = null;
        }
    }

    /**
     * 设置MediaRecorder
     */
    private void setUpMediaRecorder() throws IOException {
        Log.d(TAG, "setUpMediaRecorder!");
        //Video的设置必须遵循严格的顺序
        mMediaRecorder.reset();
//        mMediaRecorder.setProfile(profile);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

//        mMediaRecorder.setVideoEncodingBitRate(10000000);
//        mMediaRecorder.setVideoFrameRate(30);
//        mMediaRecorder.setVideoEncodingBitRate(2500000);
        mMediaRecorder.setOrientationHint(getOrientation(mSreenRotation));
//        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
//        mMediaRecorder.setVideoSize(1080,1920);
        mMediaRecorder.setProfile(profile);
//        mMediaRecorder.setVideoFrameRate(profile.videoFrameRate);
//        mMediaRecorder.setVideoEncodingBitRate(2500000);
        Log.d(TAG, "setUpMediaRecorder, video size =" + mVideoSize.getWidth() + "x" + mVideoSize.getHeight());
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + "WJVID_" + System.currentTimeMillis();
        mMediaRecorder.setOutputFile(filePath + ".mp4");
        mMediaRecorder.prepare();
    }

    /**
     * 设置Video分辨率大小
     */
    private Size getVideoSize(Size[] sizes) {
        for (Size size : sizes) {
            Log.d(TAG, "getVideoSize: " + size.getWidth() + "x" + size.getHeight());
            if (size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 1080) {

                return size;
            }
        }
        Log.e(TAG, "Couldn't find any suitable video size");
        return sizes[sizes.length - 1];
    }

    /**
     * 获取拍照方向
     */
    private int getOrientation(int rotation) {
        return ORIENTATIONS.get(rotation);
    }

    public boolean isFrontCamera() {
        return mCameraId == CameraCharacteristics.LENS_FACING_BACK;
    }

    public Size getPreviewSize() {
        return mPreviewSize;
    }

    public void switchCamera(int width, int height) {
        mCameraId ^= 1;
        Log.d(TAG, "switchCamera: mCameraId: " + mCameraId);
        releaseCamera();
        openCamera(width, height);
    }

    private Size chooseOptimalSize(Size[] sizes, int viewWidth, int viewHeight, Size pictureSize) {
        int totalRotation = getRotation();
        boolean swapRotation = totalRotation == 90 || totalRotation == 270;
        int width = swapRotation ? viewHeight : viewWidth;
        int height = swapRotation ? viewWidth : viewHeight;
        return getSuitableSize(sizes, width, height, pictureSize);
    }

    private int getRotation() {
        int displayRotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        switch (displayRotation) {
            case Surface.ROTATION_0:
                displayRotation = 90;
                break;
            case Surface.ROTATION_90:
                displayRotation = 0;
                break;
            case Surface.ROTATION_180:
                displayRotation = 270;
                break;
            case Surface.ROTATION_270:
                displayRotation = 180;
                break;
        }
        int sensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        mDisplayRotate = (displayRotation + sensorOrientation + 270) % 360;
        return mDisplayRotate;
    }

    private Size getSuitableSize(Size[] sizes, int width, int height, Size pictureSize) {
        int minDelta = Integer.MAX_VALUE; // 最小的差值，初始值应该设置大点保证之后的计算中会被重置
        int index = 0; // 最小的差值对应的索引坐标
        float aspectRatio = pictureSize.getHeight() * 1.0f / pictureSize.getWidth();
        Log.d(TAG, "getSuitableSize. aspectRatio: " + aspectRatio);
        for (int i = 0; i < sizes.length; i++) {
            Size size = sizes[i];
            // 先判断比例是否相等
            if (size.getWidth() * aspectRatio == size.getHeight()) {
                int delta = Math.abs(width - size.getWidth());
                if (delta == 0) {
                    return size;
                }
                if (minDelta > delta) {
                    minDelta = delta;
                    index = i;
                }
            }
        }
        return sizes[index];
    }

    public void handleZoom(boolean isZoomIn) {
        if (mCameraDevice == null || mCameraCharacteristics == null || mPreviewRequestBuilder == null) {
            return;
        }
        int maxZoom = mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM).intValue()
                * 10;
        Log.d(TAG, "handleZoom: maxZoom: " + maxZoom);
        Rect rect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        if (isZoomIn && mZoom < maxZoom) {
            mZoom++;
        } else if (mZoom > 1) {
            mZoom--;
        }
        Log.d(TAG, "handleZoom: mZoom: " + mZoom);
        int minW = rect.width() / maxZoom;
        int minH = rect.height() / maxZoom;
        int difW = rect.width() - minW;
        int difH = rect.height() - minH;
        int cropW = difW * mZoom / 100;
        int cropH = difH * mZoom / 100;
        cropW -= cropW & 3;
        cropH -= cropH & 3;
        Log.d(TAG, "handleZoom: cropW: " + cropW + ", cropH: " + cropH);
        Rect zoomRect = new Rect(cropW, cropH, rect.width() - cropW, rect.height() - cropH);
        mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoomRect);
        mPreviewRequest = mPreviewRequestBuilder.build();
        startPreview(); // 需要重新 start preview 才能生效
    }

    public void focusOnPoint(double x, double y, int width, int height) {
        if (mCameraDevice == null || mPreviewRequestBuilder == null) {
            return;
        }
        // 1. 先取相对于view上面的坐标
        int previewWidth = mPreviewSize.getWidth();
        int previewHeight = mPreviewSize.getHeight();
        if (mDisplayRotate == 90 || mDisplayRotate == 270) {
            previewWidth = mPreviewSize.getHeight();
            previewHeight = mPreviewSize.getWidth();
        }
        // 2. 计算摄像头取出的图像相对于view放大了多少，以及有多少偏移
        double tmp;
        double imgScale;
        double verticalOffset = 0;
        double horizontalOffset = 0;
        if (previewHeight * width > previewWidth * height) {
            imgScale = width * 1.0 / previewWidth;
            verticalOffset = (previewHeight - height / imgScale) / 2;
        } else {
            imgScale = height * 1.0 / previewHeight;
            horizontalOffset = (previewWidth - width / imgScale) / 2;
        }
        // 3. 将点击的坐标转换为图像上的坐标
        x = x / imgScale + horizontalOffset;
        y = y / imgScale + verticalOffset;
        if (90 == mDisplayRotate) {
            tmp = x;
            x = y;
            y = mPreviewSize.getHeight() - tmp;
        } else if (270 == mDisplayRotate) {
            tmp = x;
            x = mPreviewSize.getWidth() - y;
            y = tmp;
        }
        // 4. 计算取到的图像相对于裁剪区域的缩放系数，以及位移
        Rect cropRegion = mPreviewRequestBuilder.get(CaptureRequest.SCALER_CROP_REGION);
        if (cropRegion == null) {
            Log.w(TAG, "can't get crop region");
            cropRegion = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        }
        int cropWidth = cropRegion.width();
        int cropHeight = cropRegion.height();
        if (mPreviewSize.getHeight() * cropWidth > mPreviewSize.getWidth() * cropHeight) {
            imgScale = cropHeight * 1.0 / mPreviewSize.getHeight();
            verticalOffset = 0;
            horizontalOffset = (cropWidth - imgScale * mPreviewSize.getWidth()) / 2;
        } else {
            imgScale = cropWidth * 1.0 / mPreviewSize.getWidth();
            horizontalOffset = 0;
            verticalOffset = (cropHeight - imgScale * mPreviewSize.getHeight()) / 2;
        }
        // 5. 将点击区域相对于图像的坐标，转化为相对于成像区域的坐标
        x = x * imgScale + horizontalOffset + cropRegion.left;
        y = y * imgScale + verticalOffset + cropRegion.top;
        double tapAreaRatio = 0.1;
        Rect rect = new Rect();
        rect.left = clamp((int) (x - tapAreaRatio / 2 * cropRegion.width()), 0, cropRegion.width());
        rect.right = clamp((int) (x + tapAreaRatio / 2 * cropRegion.width()), 0, cropRegion.width());
        rect.top = clamp((int) (y - tapAreaRatio / 2 * cropRegion.height()), 0, cropRegion.height());
        rect.bottom = clamp((int) (y + tapAreaRatio / 2 * cropRegion.height()), 0, cropRegion.height());
        // 6. 设置 AF、AE 的测光区域，即上述得到的 rect
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle
                (rect, 1000)});
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, new MeteringRectangle[]{new MeteringRectangle
                (rect, 1000)});
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO);
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata
                .CONTROL_AE_PRECAPTURE_TRIGGER_START);
        try {
            // 7. 发送上述设置的对焦请求，并监听回调
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mAfCaptureCallback, mBackgroundHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final CameraCaptureSession.CaptureCallback mAfCaptureCallback = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result) {
            Integer state = result.get(CaptureResult.CONTROL_AF_STATE);
            if (null == state) {
                return;
            }
            Log.d(TAG, "process: CONTROL_AF_STATE: " + state);
            if (state == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED || state == CaptureResult
                    .CONTROL_AF_STATE_NOT_FOCUSED_LOCKED) {
                Log.d(TAG, "process: start normal preview");
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest
                        .CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.FLASH_MODE_OFF);
                startPreview();
            }
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                        @NonNull CaptureRequest request,
                                        @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                       @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            process(result);
        }
    };


    private void startBackgroundThread() {
        if (mBackgroundThread == null || mBackgroundHandler == null) {
            Log.v(TAG, "startBackgroundThread");
            mBackgroundThread = new HandlerThread("CameraBackground");
            mBackgroundThread.start();
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        }
    }

    private void stopBackgroundThread() {
        Log.v(TAG, "stopBackgroundThread");
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    /**
     * Compares two {@code Size}s based on their areas.
     */
    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

}
