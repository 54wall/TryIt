package xyz.ecoo.www.imagecarouseldemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;



public class ImageCarousel {
    private Context context;
    private ViewPager viewPager;
    private TextView tvTitle;
    private LinearLayout dotsRoot;
    private int time;


    private List<View> dots;//小点
    private int previousPosition = 0;//前一个被选中的position
    private List<SimpleDraweeView> simpleDraweeViewList;
    private String[] titles;//标题数组

    private ImagesPagerAdapter adapter;

    private AutoPlayThread autoPlayThread;
    private volatile boolean isExit = true;


    public ImageCarousel(Context context, ViewPager viewPager, TextView tvTitle,
                         List<View> dots, int time) {
        this.context = context;
        this.viewPager = viewPager;
        this.tvTitle = tvTitle;
        this.dots = dots;
        this.time = time;
        Log.e("image", "构造方法");
    }



    /**
     * 传入数据
     *
     * @param simpleDraweeViewList SimpleDraweeView集合
     * @param titles               标题数组
     * @return this 本身
     */
    public ImageCarousel init(List<SimpleDraweeView> simpleDraweeViewList, String[] titles) {
        this.simpleDraweeViewList = simpleDraweeViewList;
        this.titles = titles;
        Log.e("image", "init");
        autoPlayThread = new AutoPlayThread();

        return this;
    }

    /**
     * 重新加载，有待考验...
     *
     * @param simpleDraweeViewList SimpleDraweeView集合
     * @param titles               标题数组
     */
    public void reload(List<SimpleDraweeView> simpleDraweeViewList, String[] titles) {
        init(simpleDraweeViewList, titles);
        previousPosition = 0;
        start();
    }

    /**
     * 设置设配器，并实现轮播功能
     */
    public void start() {
        if (adapter != null) {
            adapter = null;
        }
        adapter = new ImagesPagerAdapter(this.simpleDraweeViewList, viewPager, context);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当被选择
            @Override
            public void onPageSelected(int position) {
                //伪无限循环，滑到最后一张图片又从新进入第一张图片
                int newPosition = position % simpleDraweeViewList.size();
                // 把当前选中的点给切换了, 还有描述信息也切换
                tvTitle.setText(titles[newPosition]);//图片下面设置显示文本
                //设置轮播点 可设置成传入的图
                dots.get(previousPosition).setBackgroundResource(R.drawable.ic_dot_focused);
                dots.get(newPosition).setBackgroundResource(R.drawable.ic_dot_normal);
                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
                previousPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setFirstLocation();
        //autoPlayThread.start();

    }

    /**
     * 设置刚打开app时显示的图片和文字
     */
    private void setFirstLocation() {
        tvTitle.setText(titles[0]);
        dots.get(0).setBackgroundResource(R.drawable.ic_dot_normal);
        viewPager.setCurrentItem(0);
    }

    /**
     * 设置是否轮播
     *
     * @param b
     */
    private void setAutoPlay(boolean b) {
        /*if (b && suspendRequested) {
            autoPlayThread.requestResume();

        } else if (!b){
            autoPlayThread.requestSuspend();
        }*/
    }


    public void stopAutoPlay() {
        if (autoPlayThread != null) {
            Log.e("thrad", "暂停");
            isExit = true;
            autoPlayThread.interrupt();
            autoPlayThread = null;
        }
    }

    /**
     * 请求继续
     */
    public void startAutoPlay() {
        Log.e("thrad", "开始");
        isExit = false;
        autoPlayThread = null;
        autoPlayThread = new AutoPlayThread();
        autoPlayThread.start();
    }

    /**
     * 自动播放线程，添加暂停和继续方法
     */
    class AutoPlayThread extends Thread {

        @Override
        public synchronized void run() {
            while (!isExit) {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    Log.e("thrad", "强制请求退出线程");
                    break;
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });

                if (this.interrupted()) {
                    Log.e("thrad", "已经是停止状态了，我要退出了");
                    break;
                }
            }


        }


    }

}
