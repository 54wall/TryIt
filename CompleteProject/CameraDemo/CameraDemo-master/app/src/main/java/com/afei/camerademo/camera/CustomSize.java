package com.afei.camerademo.camera;

import android.media.CamcorderProfile;




import java.util.Objects;

/**
 * Immutable class for describing width and height dimensions in pixels.
 */
public class CustomSize implements Comparable<CustomSize> {

    private final int mWidth;
    private final int mHeight;
    private int mFps = 30;

    /**
     * Create a new immutable Size instance.
     *
     * @param width  The width of the size, in pixels
     * @param height The height of the size, in pixels
     */
    public CustomSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public CustomSize(int width, int height, int fps) {
        mWidth = width;
        mHeight = height;
        mFps = fps;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getFps() {
        return mFps;
    }

    public void setFps(int fps) {
        mFps = fps;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof CustomSize) {
            CustomSize size = (CustomSize) o;
            return mWidth == size.mWidth && mHeight == size.mHeight && mFps == size.mFps;
        }
        return false;
    }

    @Override
    public String toString() {
        return mWidth + "x" + mHeight + " " + mFps + "p";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWidth(), getHeight(), getFps());
    }

    @Override
    public int compareTo( CustomSize another) {
        if (mWidth * mHeight == another.mWidth * another.mHeight) {
            return mFps - another.mFps;
        }
        return mWidth * mHeight - another.mWidth * another.mHeight;
    }

    public boolean hasHighSpeedCamcorder(int cameraId) {
        return hasHighSpeedCamcorder(this, cameraId);
    }

    public CamcorderProfile getCamcorderProfile() {
        if (mWidth == 720 && mHeight == 480) {
            return CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        } else if (mWidth == 1280 && mHeight == 720) {
            return CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        } else if (mWidth == 1920 && mHeight == 1080) {
            return CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
        } else if (mWidth == 3840 && mHeight == 2160) {
            return CamcorderProfile.get(CamcorderProfile.QUALITY_2160P);
        } else {
            return CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        }
    }

    public boolean hasHighSpeedCamcorder(CustomSize size, int cameraID) {
        if (size.getWidth() == 720 && size.getHeight() == 480) {
            return CamcorderProfile.hasProfile(cameraID, CamcorderProfile.QUALITY_HIGH_SPEED_480P);
        } else if (size.getWidth() == 1280 && size.getHeight() == 720) {
            return CamcorderProfile.hasProfile(cameraID, CamcorderProfile.QUALITY_HIGH_SPEED_720P);
        } else if (size.getWidth() == 1920 && size.getHeight() == 1080) {
            return CamcorderProfile.hasProfile(cameraID, CamcorderProfile.QUALITY_HIGH_SPEED_1080P);
        } else if (size.getWidth() == 3840 && size.getHeight() == 2160) {
            return CamcorderProfile.hasProfile(cameraID, CamcorderProfile.QUALITY_HIGH_SPEED_2160P);
        } else {
            return false;
        }
    }

}
