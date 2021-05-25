package xyz.ecoo.www.imagecarouseldemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ImagesPagerAdapter extends PagerAdapter {
    private List<SimpleDraweeView> simpleDraweeViewList;
    private ViewPager viewPager;
    private Context context;

    private SimpleDraweeView simpleDraweeView;

    public ImagesPagerAdapter(List<SimpleDraweeView> simpleDraweeViewList, ViewPager viewPager, Context context) {
        this.simpleDraweeViewList = simpleDraweeViewList;
        this.viewPager = viewPager;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//返回一个无限大的值，可以 无限循环
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        viewPager.removeView(simpleDraweeViewList.get(position % simpleDraweeViewList.size()));
        //super.destroyItem(container, position, object);
    }

    //是否获取缓存
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    //实例化Item
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        simpleDraweeView = simpleDraweeViewList.get(position % simpleDraweeViewList.size());
        viewPager.addView(simpleDraweeView);
        return simpleDraweeView;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

/*    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            Log.e("image","getItemPosition");
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }*/
}
