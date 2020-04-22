package pri.weiqiang.tryit.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * https://blog.csdn.net/zxzxzxy1985/article/details/45072903
 */
public class CustomWebView extends WebView {

    private static final String TAG = CustomWebView.class.getSimpleName();

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWebView(Context context) {
        super(context);

    }

    /**
     * 计算纵向范围，用于滚动到底部
     * <p>
     * (non-Javadoc)
     *
     * @see WebView#computeVerticalScrollRange()
     */
    @Override
    public int computeVerticalScrollRange() {
        Log.e(TAG, "computeVerticalScrollRange:" + super.computeVerticalScrollRange());
        Log.e(TAG, "getContentHeight():" + super.getContentHeight());
        return super.computeVerticalScrollRange();
    }
}
