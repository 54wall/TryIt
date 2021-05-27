package advert.sdk.com.advertlibrary.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import advert.sdk.com.advertlibrary.R;
import advert.sdk.com.advertlibrary.utils.FileUtils;

public class FullAdapter extends PagerAdapter {

    private String TAG = FullAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<String> pathList;
    private ViewPager viewPager;

    public FullAdapter(Context context, ViewPager viewPager, ArrayList<String> mImgList) {
        mContext = context;
        this.viewPager = viewPager;
        pathList = mImgList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_ad_full, null);
        ImageView mIvAd = (ImageView) view.findViewById(R.id.iv_ad);
        mIvAd.setImageBitmap(FileUtils.getBitmap(pathList.get(position % pathList.size())));
        mIvAd.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //判断条件中保证ViewPager首尾不会出现没有子View的情况
        if (viewPager.getChildCount() > pathList.size() + 1) {
            viewPager.removeView(viewPager.getChildAt(position % pathList.size()));
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//返回一个无限大的值，可以 无限循环
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}