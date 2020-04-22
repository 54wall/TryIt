package pri.weiqiang.tryit.seekbar.widget;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pri.weiqiang.tryit.seekbar.widget.util.LastMsgHandler;
import pri.weiqiang.tryit.seekbar.widget.util.ViewUtil;

/**
 * 用于绑定recyclerView和SeekBar的辅助类
 *
 * @author weiqiang
 * @since 2019年7月11日15:56:16
 */
public class RecyclerViewBindHelper implements SeekBar.OnSeekBarChangeListener {
    public static final long DEFAULT_TIME_OUT = 1000L;
    private static String TAG = RecyclerViewBindHelper.class.getSimpleName();
    private final SeekBar seekBar;
    private final RecyclerView recyclerView;
    boolean getBK = false;
    private int x_cur_fisrt_visible;
    private int x_end;
    private int x_cur;
    private float k;
    private float b;
    private boolean isUserSeeking;
    /*继承*/
    private VisibleHandler handler = new VisibleHandler(this);

    private RecyclerViewBindHelper(final SeekBar seekBar, RecyclerView recyclerView, final boolean isEnd) {
        Log.e(TAG, "RecyclerViewBindHelper");
        getBK = false;
        this.seekBar = seekBar;
        this.recyclerView = recyclerView;
        /*注意：如果存在数据刷新，列表长度变化，一定必须进行clearOnScrollListeners 否则recyclerView将会有多个ScrollListener
         * 项目中会发现会出现多组k,b导致rerecyclerView滚动时会来回跳转
         * */
        this.recyclerView.clearOnScrollListeners();
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager) recyclerView.getLayoutManager();
                x_cur = l.findLastVisibleItemPosition();
                x_cur_fisrt_visible = l.findFirstVisibleItemPosition();
                if (!getBK) {
                    //此时要求RecycleView务必处于最开始的位置

                    x_end = l.getItemCount();

                    if (isEnd) {
                        /*对于RecycleView已经位于最后一项时，我这里只能假设每条数据所占的高度相同，数据对称，即可见位置与SeekBar
                        位置为线性相关，所以存在y=kx+b，这样SeekBar与RecycleView移动位置的一致性较好
                        所以下边就是为了求k与b,而因为假设数据对称，所以如果列表处于最后一行，所以假设
                        然后就是
                        RecycleView                                                SeekBar
                        ------------                                                  0
                        |          |
                        |          |                                                  |
                        |          |
                        ------------x_true = x_cur-x_cur_fisrt_visible                |
                        |          |
                        |          |
                        |          |
                        |          |
                        ------------x_cur_fisrt_visible
                        |          |
                        |          |
                        |          |                                                  |
                        ------------ x_cur (位置非常接近x_end)                          |
                                     x_end                                            100

                        无论我们处于RecycleView的首尾部，我们规定
                        {
                        k*x_end+b = 100
                        k*x_true+b = 0
                        }
                        位于尾部时，此时x_cur已经非常x_end了,但实际我们为了呼应SeekBar的Progress为0到100，强行进行对应
                        计算时注意添加float
                        */
                        Log.e(TAG, "51 onScrolled x_cur_fisrt_visible:" + x_cur_fisrt_visible + ",x_cur:" + x_cur + ",x_end:" + x_end);
                        k = (float) 100 / (x_end - x_cur + x_cur_fisrt_visible);
                        b = (float) 100 - (float) (100 / (x_end - x_cur + x_cur_fisrt_visible)) * x_end;
                        Log.e(TAG, " 1 b 错误:" + b);
                        b = (float) 100 - (100 * x_end / (float) (x_end - x_cur + x_cur_fisrt_visible));
                        Log.e(TAG, " 2 b 正确:" + b);

                    } else {
                       /*对于RecycleView已经位于最后一项时，我这里只能假设每条数据所占的高度相同，数据对称，即可见位置与SeekBar
                        位置为线性相关，所以存在y=kx+b，这样SeekBar与RecycleView移动位置的一致性较好
                        所以下边就是为了求k与b,而因为假设数据对称，所以如果列表处于最后一行，所以假设
                        然后就是
                        RecycleView                                                SeekBar
                        ------------                                                  0
                        |          |
                        |          |                                                  |
                        |          |
                        ------------x_cur
                        |          |
                        |          |
                        |          |
                        |          |

                        |          |
                        |          |
                        |          |                                                  |
                        ------------ x_end                                            100


                        无论我们处于RecycleView的首尾部，我们规定
                        {
                        k*x_end+b = 100
                        k*x_cur+b = 0
                        }
                        位于尾部时，此时x_cur已经非常x_end了,但实际我们为了呼应SeekBar的Progress为0到100，强行进行对应
                        计算时注意添加float
                        */
                        Log.e(TAG, "56 onScrolled x_cur_fisrt_visible:" + x_cur_fisrt_visible + ",x_cur:" + x_cur + ",x_end:" + x_end);
                        k = (float) 100 / (x_end - x_cur);
                        b = (float) 100 * x_cur / (x_cur - x_end);
                    }

                    getBK = true;
                }
                Log.e(TAG, "x_cur:" + x_cur + ",x_cur_fisrt_visible:" + x_cur_fisrt_visible);
                int i;
                //保证RecycleView滑到首尾时，SeekBar不会留有间隙
                if (x_cur == x_end - 1) {
                    i = 100;
                } else if (x_cur_fisrt_visible == 0) {
                    i = 0;
                } else {
                    i = (int) (k * x_cur + b);
                }
                Log.e(TAG, "k:" + k + ",b:" + b + ",i:" + i + ",x_cur:" + x_cur);
                //用户触控时不触发
                if (isUserSeeking) {
                    return;
                }
                seekBar.setProgress(i);
            }
        });

    }

    /**
     * 使用静态方法来绑定逻辑，代码可读性更高。
     */
    public static RecyclerViewBindHelper bind(SeekBar seekBar, RecyclerView recyclerView, boolean isLast) {
        Log.e(TAG, "RecyclerViewBindHelper");
        //初始化工具类
        ViewUtil.init(seekBar.getContext().getApplicationContext());
        RecyclerViewBindHelper helper = new RecyclerViewBindHelper(seekBar, recyclerView, isLast);
        seekBar.setOnSeekBarChangeListener(helper);
        return helper;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e(TAG, "onProgressChanged progress:" + progress + ",x_end:" + x_end + ",fromUser:" + fromUser);
        showScroll();

        if (!isUserSeeking) {
            handler.reset();
        }

        //不是用户操作的时候不触发
        if (!fromUser) {
            return;
        }

        if (x_end > 0) {
            if (progress == 0) {
                recyclerView.scrollToPosition(0);
            } else {
                recyclerView.scrollToPosition(progress * x_end / 100 - 1);
            }

        }
    }

    /*动画*/

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "onStartTrackingTouch");
        isUserSeeking = true;
        handler.clearAll();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "onStopTrackingTouch");
        isUserSeeking = false;
        handler.reset();
    }

    private void hideScroll() {
        Log.e(TAG, "hideScroll");
        seekBar.setVisibility(View.GONE);
    }

    private void showScroll() {
        Log.e(TAG, "showScroll");
        seekBar.setVisibility(View.VISIBLE);
    }

    private static class VisibleHandler extends LastMsgHandler {


        private RecyclerViewBindHelper helper;

        public VisibleHandler(RecyclerViewBindHelper helper) {
            Log.e(TAG, "VisibleHandler");
            this.helper = helper;
        }

        public void reset() {
            Log.e(TAG, "reset");
            sendMsgDelayed(DEFAULT_TIME_OUT);
        }

        @Override
        protected void handleLastMessage(Message msg) {
            Log.e(TAG, "handleLastMessage");
            helper.hideScroll();
        }
    }

}
