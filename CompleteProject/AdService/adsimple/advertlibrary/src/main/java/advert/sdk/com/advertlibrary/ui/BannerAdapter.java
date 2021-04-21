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

public class BannerAdapter extends PagerAdapter {
    private final String TAG = BannerAdapter.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<String> pathList;

    public BannerAdapter(Context context, ArrayList<String> pathList) {
        mContext = context;
        this.pathList = pathList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_ad_banner, null);
        ImageView mIvAd = (ImageView) view.findViewById(R.id.iv_ad);
        mIvAd.setImageBitmap(FileUtils.getBitmap(pathList.get(position)));
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

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}