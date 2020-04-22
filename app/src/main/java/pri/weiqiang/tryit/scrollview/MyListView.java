package pri.weiqiang.tryit.scrollview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Apple on 2016/9/23.
 */

public class MyListView extends ListView {
    private String TAG = MyListView.class.getSimpleName();
    private float lastY;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e(TAG, "dispatchTouchEvent: ACTION_DOWN");
            getParent().getParent().requestDisallowInterceptTouchEvent(true);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e(TAG, "dispatchTouchEvent: ACTION_MOVE");
            if (lastY > ev.getY()) {
                Log.e(TAG, "dispatchTouchEvent: lastY > ev.getY()");
                // 如果是向上滑动，且不能滑动了，则让ScrollView处理
                if (!canScrollList(1)) {
                    Log.e(TAG, "dispatchTouchEvent: ACTION_MOVE 向上滑动，且不能滑动了");
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            } else if (ev.getY() > lastY) {
                Log.e(TAG, "dispatchTouchEvent: ACTION_MOVE ev.getY() > lastY");
                // 如果是向下滑动，且不能滑动了，则让ScrollView处理
                if (!canScrollList(-1)) {
                    Log.e(TAG, "dispatchTouchEvent: ACTION_MOVE 向下滑动，且不能滑动了");
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
        }
        lastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }

}
