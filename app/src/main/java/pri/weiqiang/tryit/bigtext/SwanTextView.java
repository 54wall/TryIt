package pri.weiqiang.tryit.bigtext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class SwanTextView extends TextView {

    private int mPreBottom = -1;
    private OnPreDrawListener preDrawListener = null;

    public SwanTextView(Context context) {
        super(context);
    }

    public SwanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwanTextView(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mPreBottom != getBottom()) {
            mPreBottom = getBottom();

            if (preDrawListener != null) {
                preDrawListener.onPreDraw(mPreBottom);
            }
        }

        super.onDraw(canvas);
    }

    public void setOnPreDrawListener(OnPreDrawListener listener) {
        preDrawListener = listener;
    }

    public interface OnPreDrawListener {

        void onPreDraw(int bottom);
    }
}

