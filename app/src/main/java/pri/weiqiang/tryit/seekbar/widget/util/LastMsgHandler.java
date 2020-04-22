package pri.weiqiang.tryit.seekbar.widget.util;

import android.os.Handler;
import android.os.Message;

/**
 * 只响应最后一次请求的Handler
 *
 * @author DrkCore
 * @since 2016年4月17日22:49:20
 */
public abstract class LastMsgHandler extends Handler {

    private int count = 0;

    /**
     * 增加Count数。你必须先调用该方法后再使用sendMessageXXX
     */
    public synchronized final void increaseCount() {
        count++;
    }

    public final void sendMsg() {
        sendMsgDelayed(0);
    }

    public final void sendMsgDelayed(long delay) {
        increaseCount();
        if (delay <= 0) {
            sendEmptyMessage(0);
        } else {
            sendEmptyMessageDelayed(0, delay);
        }
    }

    public synchronized final void clearAll() {
        count = 0;
        removeCallbacksAndMessages(null);
    }

    @Override
    public synchronized final void handleMessage(Message msg) {
        super.handleMessage(msg);
        count--;
        if (count < 0) {
            throw new IllegalStateException("count数异常");
        }

        if (count == 0) {
            handleLastMessage(msg);
        }
    }

    /*回调*/

    protected abstract void handleLastMessage(Message msg);
}
