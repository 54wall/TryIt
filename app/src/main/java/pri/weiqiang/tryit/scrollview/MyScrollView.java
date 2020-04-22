package pri.weiqiang.tryit.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Apple on 2016/9/22.
 */

public class MyScrollView extends ScrollView {
    private String TAG = MyScrollView.class.getSimpleName();

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e(TAG, "onInterceptTouchEvent ACTION_DOWN");
            // 因为ACTION_DOWN始终无法进入ScrollView的onTouchEvent，
            // 但是ScrollView的滚动需要在ACTION_DOWN时做一些准备
            onTouchEvent(ev);
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e(TAG, "onTouchEvent ACTION_MOVE");
            //can do something
        }
        return super.onTouchEvent(ev);
    }

}
