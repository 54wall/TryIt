package pri.weiqiang.tryit.longclick;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 * Created by majl on 2017/11/7.
 * ---------------------
 * 作者：庭然
 * 来源：CSDN
 * 原文：https://blog.csdn.net/lili625/article/details/78467180
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
class LongClickUtils {

    private static final String TAG = "LongClickUtils";

    /**
     * @param handler           外界handler(为了减少handler的泛滥使用,最好全局传handler引用,如果没有就直接传 new Handler())
     * @param longClickView     被长按的视图(任意控件)
     * @param delayMillis       长按时间,毫秒
     * @param longClickListener 长按回调的返回事件
     */
    public static void setLongClick(final Handler handler, final View longClickView, final long delayMillis, final OnLongClickListener longClickListener) {
        longClickView.setOnTouchListener(new OnTouchListener() {
            private int TOUCH_MAX = 50;
            private int mLastMotionX;
            private int mLastMotionY;
            private Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (longClickListener != null) {// 回调给用户,用户可能传null,需要判断null
                        longClickListener.onLongClick(longClickView);
                    }
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 抬起时,移除已有Runnable回调,抬起就算长按了(不需要考虑用户是否长按了超过预设的时间)
                        handler.removeCallbacks(r);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(mLastMotionX - x) > TOUCH_MAX
                                || Math.abs(mLastMotionY - y) > TOUCH_MAX) {
                            // 移动误差阈值
                            // xy方向判断
                            // 移动超过阈值，则表示移动了,就不是长按(看需求),移除 已有的Runnable回调
                            handler.removeCallbacks(r);
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        // 每次按下重新计时
                        // 按下前,先移除 已有的Runnable回调,防止用户多次单击导致多次回调长按事件的bug
                        handler.removeCallbacks(r);
                        mLastMotionX = x;
                        mLastMotionY = y;
                        // 按下时,开始计时
                        handler.postDelayed(r, delayMillis);
                        break;
                }
                return true;//onclick等其他事件不能用请改这里
            }
        });
    }

}