package pri.weiqiang.tryit.camerahub;

public interface ICamera {
    void open();

    void startPreview();

    void stopPreview();

    void close();

    void setPreviewCallback(PreviewCallback pc);

    interface PreviewCallback {
        void onPreviewFrame(byte[] data, int width, int height);
    }
}
