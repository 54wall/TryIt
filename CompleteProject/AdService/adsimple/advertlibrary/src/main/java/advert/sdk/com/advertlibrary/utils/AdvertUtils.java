package advert.sdk.com.advertlibrary.utils;

import advert.sdk.com.advertlibrary.ui.AdPresentation;

public class AdvertUtils {
    private volatile static AdvertUtils instance = null;
    private static final String TAG = AdvertUtils.class.getSimpleName();
    private AdPresentation mAdPresentation;

    public static AdvertUtils getInstance() {
        if (instance == null) {
            synchronized (AdvertUtils.class) {
                if (instance == null) {
                    instance = new AdvertUtils();
                }
            }
        }
        return instance;
    }

    public void initLoopView(AdPresentation mPresentation) {
        mAdPresentation = mPresentation;
    }

    public void showLoopView(int adType) {
        if (mAdPresentation != null) {
            if (mAdPresentation.isExitZip()) {
                mAdPresentation.initAdsData();
                mAdPresentation.setAdType(adType);
                if (!mAdPresentation.isShowing()) {
                    mAdPresentation.show();
                }
            }
        }
    }

    public void remove() {
        try {
            if (mAdPresentation != null) {
                mAdPresentation.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        try {
            if (mAdPresentation != null) {
                mAdPresentation.cancel();
                mAdPresentation = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
