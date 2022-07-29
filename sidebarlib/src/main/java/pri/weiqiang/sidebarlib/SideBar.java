package pri.weiqiang.sidebarlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * ListView侧边按照A-Z排序的SideBar控件
 *
 * @author Test
 */
public class SideBar extends View {

    // SideBar上显示的字母和#号
    private static final String[] CHARACTERS = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z"};
    private String TAG = SideBar.class.getSimpleName();
    // SideBar的高度
    private int width = 400;
    // SideBar的宽度
    private int height = 400;
    // SideBar中每个字母的显示区域的高度
    private float cellHeight;
    // 画字母的画笔
    private Paint characterPaint;
    // SideBar上字母绘制的矩形区域
    private Rect textRect;
    // 手指触摸在SideBar上的横纵坐标
    private float touchY;
    private float touchX;

    private OnSelectListener listener;

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG,"super(context, attrs, defStyleAttr)");
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG,"SideBar(Context context, AttributeSet attrs)");
        init(context);
    }

    public SideBar(Context context) {
        super(context);
        Log.e(TAG,"SideBar(Context context)");
        init(context);
    }

    // 初始化操作
    private void init(Context context) {
        Log.e(TAG,"init");
        textRect = new Rect();
        characterPaint = new Paint();
//        characterPaint.setColor(Color.parseColor("#BEBEBE"));
        characterPaint.setColor(getResources().getColor(R.color.colorSiderBar));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG,"onLayout");
        if (changed) { // 在这里测量SideBar的高度和宽度
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            Log.e(TAG, "width:" + width + ",height:" + height);
            // SideBar的高度除以需要显示的字母的个数，就是每个字母显示区域的高度
            cellHeight = height * 1.0f / CHARACTERS.length;
            // 根据SideBar的宽度和每个字母显示的高度，确定绘制字母的文字大小，这样处理的好处是，对于不同分辨率的屏幕，文字大小是可变的
            int textSize = (int) ((width > cellHeight ? cellHeight : width) * (3.0f / 4));
            characterPaint.setTextSize(textSize);
        }
    }

    // 画出SideBar上的字母
    private void drawCharacters(Canvas canvas) {
        Log.e(TAG,"drawCharacters");
        for (int i = 0; i < CHARACTERS.length; i++) {
            String s = CHARACTERS[i];
            // 获取画字母的矩形区域
            characterPaint.getTextBounds(s, 0, s.length(), textRect);
            // 根据上一步获得的矩形区域，画出字母
            canvas.drawText(s, (width - textRect.width()) / 2f, cellHeight * i + (cellHeight + textRect.height()) / 2f, characterPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG,"onDraw");
        drawCharacters(canvas);
    }

    // 根据手指触摸的坐标，获取当前选择的字母
    private String getHint() {
        int index = (int) (touchY / cellHeight);
        if (index >= 0 && index < CHARACTERS.length) {
            return CHARACTERS[index];
        }
        /*
         * weiqiang 在siderbar中统一进行修改，避免了每次在sideBar.setOnSelectListener的多处修改 避免点击siderbar两端时越界，报错：E/InputEventReceiver Exception dispatching input event.
         */
        if (index < 0) {
            return CHARACTERS[0];
        }
        return CHARACTERS[CHARACTERS.length - 1];
        // return null;//old
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // 获取手指触摸的坐标
                touchX = event.getX();
                touchY = event.getY();
                if (listener != null && touchX > 0) {
                    listener.onSelect(getHint());
                }
                if (listener != null && touchX < 0) {
                    listener.onMoveUp(getHint());
                }
                return true;
            case MotionEvent.ACTION_UP:
                touchY = event.getY();
                if (listener != null) {
                    listener.onMoveUp(getHint());
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    // 设置监听器
    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    // 监听器，监听手指在SideBar上按下和抬起的动作
    public interface OnSelectListener {
        void onSelect(String s);

        void onMoveUp(String s);
    }

}
