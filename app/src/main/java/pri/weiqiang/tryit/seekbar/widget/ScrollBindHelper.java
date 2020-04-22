package pri.weiqiang.tryit.seekbar.widget;

import android.os.Message;
import android.view.View;
import android.widget.SeekBar;

import androidx.core.widget.NestedScrollView;

import pri.weiqiang.tryit.seekbar.widget.util.LastMsgHandler;
import pri.weiqiang.tryit.seekbar.widget.util.ViewUtil;

/**
 * 用于绑定ScrollView和SeekBar的辅助类
 *
 * @author DrkCore
 * @since 2016年3月28日18:10:17
 */
public class ScrollBindHelper implements SeekBar.OnSeekBarChangeListener, NestedScrollView.OnScrollChangeListener {

    public static final long DEFAULT_TIME_OUT = 1000L;
    private final SeekBar seekBar;
    private final NestedScrollView scrollView;
    private final View scrollContent;
    private boolean isUserSeeking;

    /*继承*/
    private VisibleHandler handler = new VisibleHandler(this);

    private ScrollBindHelper(SeekBar seekBar, NestedScrollView scrollView) {
        this.seekBar = seekBar;
        this.scrollView = scrollView;
        this.scrollContent = scrollView.getChildAt(0);
    }

    /**
     * 使用静态方法来绑定逻辑，代码可读性更高。
     */
    public static ScrollBindHelper bind(SeekBar seekBar, NestedScrollView scrollView) {
        //初始化工具类
        ViewUtil.init(seekBar.getContext().getApplicationContext());

        ScrollBindHelper helper = new ScrollBindHelper(seekBar, scrollView);
        seekBar.setOnSeekBarChangeListener(helper);
        scrollView.setOnScrollChangeListener(helper);
        return helper;
    }

    private int getContentRange() {
        return scrollContent.getHeight();
    }

    private int getScrollRange() {
        return scrollContent.getHeight() - scrollView.getHeight();
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //用户触控时不触发
        if (isUserSeeking) {
            return;
        } else if (getContentRange() < ViewUtil.getScreenHeightPx() * 3) {//宽度小于三个屏幕不做处理
            return;
        }

        int range = getScrollRange();
        seekBar.setProgress(range != 0 ? scrollY * 100 / range : 0);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        showScroll();

        if (!isUserSeeking) {
            handler.reset();
        }

        //不是用户操作的时候不触发
        if (!fromUser) {
            return;
        }

        scrollView.scrollTo(0, progress * getScrollRange() / 100);
    }

    /*动画*/

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isUserSeeking = true;
        handler.clearAll();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isUserSeeking = false;
        handler.reset();
    }

    private void hideScroll() {
        seekBar.setVisibility(View.GONE);
    }

    private void showScroll() {
        seekBar.setVisibility(View.VISIBLE);
    }

    private static class VisibleHandler extends LastMsgHandler {

        private ScrollBindHelper helper;

        public VisibleHandler(ScrollBindHelper helper) {
            this.helper = helper;
        }

        public void reset() {
            sendMsgDelayed(DEFAULT_TIME_OUT);
        }

        @Override
        protected void handleLastMessage(Message msg) {
            helper.hideScroll();
        }
    }

}
