package pri.weiqiang.tryit.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import pri.weiqiang.tryit.R;

public class CircleView extends View {

    private String TAG = CircleView.class.getSimpleName();

    private int circleColor;
    private int arcColor;
    private int textColor;
    private float textSize;
    private String text;
    private int startAngle;
    private int sweepAngle;
    private int mCircleXY;
    private float mRadius;

    private Paint mCirclePaint;
    private RectF mRectF;
    private Paint mArcPaint;
    private Paint mTextPaint;

    private int width;
    private int height;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        initView(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(21)
    private CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        printWH();
        init();
    }

    private void initView(Context context, AttributeSet attrs) {
        Log.e(TAG, "nitView");
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.circleView);
        if (ta != null) {
            circleColor = ta.getColor(R.styleable.circleView_circleColor, Color.BLUE);
            arcColor = ta.getColor(R.styleable.circleView_arcColor, Color.RED);
            textColor = ta.getColor(R.styleable.circleView_textColor, Color.GREEN);
            textSize = ta.getDimension(R.styleable.circleView_textSize, 50);
            text = ta.getString(R.styleable.circleView_text);
            startAngle = ta.getInt(R.styleable.circleView_startAngle, 0);
            sweepAngle = ta.getInt(R.styleable.circleView_sweepAngle, 90);
            ta.recycle();
        }
    }

    //getMeasuredWidth getMeasuredHeight 在onMeasure之后才不为0
    private void init() {
        Log.e(TAG, "init");
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        int length = Math.min(width, height);
        mCircleXY = length / 2;
        mRadius = length * 0.5f / 2;
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(circleColor);
        mRectF = new RectF(length * 0.1f, length * 0.1f, length * 0.9f,
                length * 0.9f);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(arcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth((width * 0.1f));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSth(canvas);
        printWH();

    }

    private void drawSth(Canvas canvas) {
        Log.e(TAG, "drawSth:" + canvas + "mCircleXY:" + mCircleXY + "mRadius:" + mRadius + "mCirclePaint:" + mCirclePaint);
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mArcPaint);
        canvas.drawText(text, 0, text.length(), mCircleXY, mCircleXY + textSize
                / 4, mTextPaint);
    }

    private void printWH() {
        Log.e(TAG, "148 getMeasuredWidth:" + getMeasuredWidth());
        Log.e(TAG, "148 getMeasuredHeight:" + getMeasuredHeight());
        Log.e(TAG, "148 getWidth:" + getWidth());
        Log.e(TAG, "148 getHeight:" + getHeight());
    }


}
