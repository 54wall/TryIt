package advert.sdk.com.advertlibrary.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import advert.sdk.com.advertlibrary.R;
import advert.sdk.com.advertlibrary.utils.FileUtils;

public class FullAdapter extends PagerAdapter {
    private final Context mContext;
    private final ArrayList<String> pathList;

    public FullAdapter(Context context, ArrayList<String> mImgList) {
        mContext = context;
        pathList = mImgList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_ad_full, null);
        ImageView mIvAd = (ImageView) view.findViewById(R.id.iv_ad);
        mIvAd.setImageBitmap(FileUtils.getBitmap(pathList.get(position)));
        mIvAd.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pathList.size();
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}