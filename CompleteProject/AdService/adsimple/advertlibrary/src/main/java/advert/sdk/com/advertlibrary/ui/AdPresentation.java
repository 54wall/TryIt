package advert.sdk.com.advertlibrary.ui;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import advert.sdk.com.advertlibrary.R;
import advert.sdk.com.advertlibrary.bean.AdBean;
import advert.sdk.com.advertlibrary.constant.AdvertConstant;
import advert.sdk.com.advertlibrary.utils.AdvertUtils;
import advert.sdk.com.advertlibrary.utils.FileUtils;
import advert.sdk.com.advertlibrary.utils.ZipUtils;

public class AdPresentation extends Presentation {
    private boolean isRunning = false;
    private Thread swapThread;
    private BannerAdapter bannerAdapter;
    private FullAdapter fullAdapter;
    private final String TAG = AdPresentation.class.getSimpleName();
    private final ArrayList<String> adsFullList = new ArrayList<>();
    private final ArrayList<String> adsBannerList = new ArrayList<>();
    private ViewPager mVpAdFull;
    private LinearLayout mLlDotsFull;
    private FrameLayout mFlAdFull;
    private LinearLayout mLlAd;
    private TextView mTvVpFull;
    private ViewPager mVpAdBanner;
    private LinearLayout mLlDotsBanner;
    private FrameLayout mFlAdBanner;
    private TextView mTvVpBanner;
    private int maxFull = 0;
    private int maxBanner = 0;
    private int previousSelectedPositionFull = 0;
    private int currentPositionFull = 0;
    private int previousSelectedPositionBanner = 0;
    private int currentPositionBanner = 0;
    private final Context context;
    private int interval = 5000;
    private float mTouchSlop;

    public AdPresentation(Context context, Display display) {
        super(context, display);
        this.context = context;
    }

    public AdPresentation(Context context, Display display, int theme) {
        super(context, display, theme);
        this.context = context;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //整个dialog背景透明，才会使APP界面显示出来
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.presentation_ad);
        initView();
        initAdsData();
        setAdType(AdvertConstant.AD_SEC_CUR);
    }

    private boolean unzipAds() {
        FileUtils.isFolderExists(AdvertConstant.PATH_PROJECT);
        if (FileUtils.fileIsExists(AdvertConstant.PATH_ADS_ZIP)) {
            try {
                ZipUtils.unzipFile(
                        AdvertConstant.PATH_ADS_ZIP,
                        AdvertConstant.PATH_ADS
                );
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "unzipFile e:" + e.toString());
                return false;
            }


        } else {
            Log.e(TAG, "unzipAds() not exist ");
            AdvertUtils.getInstance().remove();
            return false;
        }
    }

    private void initView() {
        mLlAd = (LinearLayout) findViewById(R.id.ll_ad);
        mVpAdFull = (ViewPager) findViewById(R.id.vp_ad_full);
        changeViewPagerSpeed(mVpAdFull);
        mLlDotsFull = (LinearLayout) findViewById(R.id.ll_dots_full);
        mFlAdFull = (FrameLayout) findViewById(R.id.fl_ad_full);
        mTvVpFull = (TextView) findViewById(R.id.tv_vp_full);
        mVpAdBanner = (ViewPager) findViewById(R.id.vp_ad_banner);
        mLlDotsBanner = (LinearLayout) findViewById(R.id.ll_dots_banner);
        mFlAdBanner = (FrameLayout) findViewById(R.id.fl_ad_banner);
        mTvVpBanner = (TextView) findViewById(R.id.tv_vp_banner);
    }

    private void changeViewPagerSpeed(ViewPager mViewPager){
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(),
                    new AccelerateInterpolator());
            field.set(mViewPager, scroller);
            scroller.setmDuration(1000);
        } catch (Exception e) {

        }
    }
    public void setAdType(int adType) {
        switch (adType) {
            case AdvertConstant.AD_SEC_FULL:
                mFlAdBanner.setVisibility(View.GONE);
                if (maxFull != 0) {
                    mFlAdFull.setVisibility(View.VISIBLE);
                }
                break;
            case AdvertConstant.AD_SEC_BANNER:
                if (maxBanner != 0) {
                    mFlAdBanner.setVisibility(View.VISIBLE);
                }
                mFlAdFull.setVisibility(View.GONE);
                break;
            case AdvertConstant.AD_SEC_INIT:
                //仅更新数据，并不更改当前界面时全屏还是仅标题头
                initAdsData();
                break;
        }
    }

    public void initAdsData() {
        //有文件夹但没有json文件,则先解压缩
        if (FileUtils.fileIsExists(AdvertConstant.PATH_ADS_CONF)) {
            try {
                String result = FileUtils.readFile(context, AdvertConstant.PATH_ADS_CONF);
                Gson gson = new Gson();
                AdBean adBean = gson.fromJson(result, AdBean.class);
                adsFullList.clear();
                adsBannerList.clear();
                mLlDotsFull.removeAllViews();
                mLlDotsBanner.removeAllViews();
                mVpAdFull.clearOnPageChangeListeners();
                mVpAdBanner.clearOnPageChangeListeners();
                interval = adBean.getInterval() * 1000;
                for (int i = 0; i < adBean.getAdsBannerList().size(); i++) {
                    adsBannerList.add(AdvertConstant.PATH_ADS_ROOT + adBean.getAdsBannerList().get(i).getName());
                }
                bannerAdapter = new BannerAdapter(context, adsBannerList);
                for (int i = 0; i < adBean.getAdsFullList().size(); i++) {
                    adsFullList.add(AdvertConstant.PATH_ADS_ROOT + adBean.getAdsFullList().get(i).getName());
                }

                fullAdapter = new FullAdapter(context, mVpAdFull,adsFullList);

                mVpAdFull.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        //伪无限循环，滑到最后一张图片又从新进入第一张图片
                        int newPosition = i % adsFullList.size();
                        //设置轮播点 可设置成传入的图
                        mLlDotsFull.getChildAt(previousSelectedPositionFull).setEnabled(false);
                        mLlDotsFull.getChildAt(newPosition).setEnabled(true);
                        // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
                        previousSelectedPositionFull = newPosition;
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                ViewConfiguration configuration = ViewConfiguration.get(context);
                mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
                mVpAdFull.setOnTouchListener(new View.OnTouchListener() {
                    int touchFlag = 0;
                    float x = 0, y = 0;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                touchFlag = 0;
                                x = event.getX();
                                y = event.getY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                float xDiff = Math.abs(event.getX() - x);
                                float yDiff = Math.abs(event.getY() - y);
                                if (xDiff < mTouchSlop && xDiff >= yDiff)
                                    touchFlag = 0;
                                else
                                    touchFlag = -1;
                                break;
                            case MotionEvent.ACTION_UP:
                                if (touchFlag == 0) {
                                    AdvertUtils.getInstance().remove();
                                }
                                break;
                        }
                        return false;
                    }
                });
                mVpAdBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        currentPositionBanner = i;
                        mLlDotsBanner.getChildAt(previousSelectedPositionBanner).setEnabled(false);
                        mLlDotsBanner.getChildAt(currentPositionBanner).setEnabled(true);
                        previousSelectedPositionBanner = currentPositionBanner;
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                maxBanner = adsBannerList.size();
                maxFull = adsFullList.size();
                // 文本描述
                View dotFull;
                LinearLayout.LayoutParams layoutParams;

                for (int i = 0; i < maxFull; i++) {

                    //加引导点
                    dotFull = new View(context);
                    dotFull.setBackgroundResource(R.drawable.dot);
                    layoutParams = new LinearLayout.LayoutParams(10, 10);
                    if (i != 0) {
                        layoutParams.leftMargin = 10;
                    }
                    //设置默认所有都不可用
                    dotFull.setEnabled(false);
                    mLlDotsFull.addView(dotFull, layoutParams);
                }
                View dotBanner;
                for (int i = 0; i < maxBanner; i++) {

                    //加引导点
                    dotBanner = new View(context);
                    dotBanner.setBackgroundResource(R.drawable.dot);
                    layoutParams = new LinearLayout.LayoutParams(10, 10);
                    if (i != 0) {
                        layoutParams.leftMargin = 10;
                    }
                    //设置默认所有都不可用
                    dotBanner.setEnabled(false);
                    mLlDotsBanner.addView(dotBanner, layoutParams);
                }
                mVpAdBanner.setAdapter(bannerAdapter);
                mVpAdFull.setAdapter(fullAdapter);
                mLlDotsFull.getChildAt(0).setEnabled(true);
                previousSelectedPositionFull = 0;
                mVpAdFull.setCurrentItem(0);
                previousSelectedPositionBanner = 0;
                mVpAdBanner.setCurrentItem(0);
                isRunning = true;
                if (swapThread != null && swapThread.isAlive()) {
                    Log.e(TAG, "start: swapThread is alive");
                } else {
                    swapThread = new Thread() {
                        @Override
                        public void run() {
                            while (isRunning) {
                                try {
                                    Thread.sleep(interval);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mLlAd.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (maxBanner != 0) {
                                            currentPositionBanner = (currentPositionBanner + 1) % maxBanner;
                                            mVpAdBanner.setCurrentItem(currentPositionBanner);
                                        }
                                        if (maxFull != 0) {
                                            mVpAdFull.setCurrentItem(mVpAdFull.getCurrentItem()+1);
                                        }
                                    }
                                });
                            }
                        }
                    };
                    swapThread.start();
                }

            } catch (Exception e) {
                Log.e(TAG, "e " + e.toString());
            }

        } else {
            try {
                boolean unzip = unzipAds();
                Log.e(TAG, "unzip:" + unzip);
                if (unzip) {
                    initAdsData();
                }
            } catch (Exception e) {
                Log.e(TAG, "unzipAds() e:" + e.getMessage());
            }

        }
    }

    public boolean isExitZip() {
        return unzipAds();
    }

    public void destoryThread() {
        if (swapThread != null && swapThread.isAlive()) {
            isRunning = false;
        }
    }
}
