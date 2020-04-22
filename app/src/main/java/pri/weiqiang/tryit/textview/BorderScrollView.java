package pri.weiqiang.tryit.textview;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-9-6
 * Time: 下午2:06
 */
public class BorderScrollView extends ScrollView {
    private static String TAG = BorderScrollView.class.getSimpleName();
    private long millis;
    // 滚动监听者
    private OnScrollChangedListener onScrollChangedListener;

    public BorderScrollView(Context context) {
        super(context);
        Log.e(TAG, "BorderScrollView(Context context)");
    }

    public BorderScrollView(Context context, AttributeSet attrs) {

        super(context, attrs);
        Log.e(TAG, "(Context context, AttributeSet attrs)");
    }

    public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        Log.e(TAG, "(Context context, AttributeSet attrs, int defStyle)");
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.e(TAG, "41 onScrollChanged:" + "l" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
        super.onScrollChanged(l, t, oldl, oldt);

        if (null == onScrollChangedListener) {
            Log.e(TAG, "45 onScrollChanged:" + "l" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
            return;
        }

        long now = System.currentTimeMillis();

        // 通知监听者当前滚动的具体信息
        onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);

        if (now - millis > 1000L) {
            Log.e(TAG, "54 onScrollChanged:" + "l" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
            // 滚动到底部（前提：从不是底部滚动到底部）
            if ((this.getHeight() + oldt) != this.getTotalVerticalScrollRange()
                    && (this.getHeight() + t) == this.getTotalVerticalScrollRange()) {
                Log.e(TAG, "滚动到底部 58 onScrollChanged:" + "l" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
                onScrollChangedListener.onScrollBottom(); // 通知监听者滚动到底部

                millis = now;
            }
        }

        // 滚动到顶部（前提：从不是顶部滚动到顶部）
        if (oldt != 0 && t == 0) {
            Log.e(TAG, "滚动到顶部 68 onScrollChanged:" + "l" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
            onScrollChangedListener.onScrollTop(); // 通知监听者滚动到顶部
        }


    }

    public OnScrollChangedListener getOnScrollChangedListener() {
        Log.e(TAG, "getOnScrollChangedListener");
        return onScrollChangedListener;
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        Log.e(TAG, "setOnScrollChangedListener");
        this.onScrollChangedListener = onScrollChangedListener;
    }

    /**
     * 获得滚动总长度
     */
    public int getTotalVerticalScrollRange() {
        Log.e(TAG, "getTotalVerticalScrollRange");
        return this.computeVerticalScrollRange();
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        Log.e(TAG, "computeScrollDeltaToGetChildRectOnScreen");
        // 禁止ScrollView在子控件的布局改变时自动滚动
        return 0;
    }

}