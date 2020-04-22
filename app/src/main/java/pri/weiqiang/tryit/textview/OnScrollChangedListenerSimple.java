package pri.weiqiang.tryit.textview;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-9-9
 * Time: 下午2:39
 */
public class OnScrollChangedListenerSimple implements OnScrollChangedListener {
    private static String TAG = "Simple";

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.e(TAG, "onScrollChanged");
    }

    @Override
    public void onScrollTop() {
        Log.e(TAG, "onScrollTop");
    }

    @Override
    public void onScrollBottom() {
        Log.e(TAG, "onScrollBottom");
    }
}
