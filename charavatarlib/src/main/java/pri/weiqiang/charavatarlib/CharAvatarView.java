package pri.weiqiang.charavatarlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HugoXie on 16/9/14.
 * <p>
 * Email: Hugo3641@gmail.com GitHub: https://github.com/xcc3641 Info: 根据用户名随机生成头像
 */
public class CharAvatarView extends AppCompatImageView {

    private static final String TAG = CharAvatarView.class.getSimpleName();
    // 颜色画板集
    // 绿色 0xff95C956, 橙色 0xffEA742C, 蓝色 0xff5B78CE, 黑色 0xff4C515B, 橘黄0xffFDC645
    private static final int[] colors = {
            // weiqiang 颜色暂时不使用多彩，仅使用蓝色
            0xff95C956, 0xffEA742C, 0xff5B78CE, 0xff4C515B, 0xffFDC645};

    private int color = 0xff2e6cbd;// 单独一种颜色
    private Paint mPaintBackground;
    private Paint mPaintText;
    private Rect mRect;
    private boolean isMoreText;// weiqiang 表示文字是否过多，过多则文字绘制字体时要小一些

    private String text;

    private int charHash;

    public CharAvatarView(Context context) {

        this(context, null);
    }

    public CharAvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CharAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // 宽高相同
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != text) {
            // weiqiang 取随机色 color不能作为全局变量同继承BaseAdapter的类一样
            int color = colors[charHash % colors.length];
            mPaintBackground.setColor(color);
            /*
             * 画圆 canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaintBackground); 画矩形 canvas.drawRect(0, 0, getWidth(), getWidth(), mPaintBackground);
             */
            // weiqiang 画圆角矩形
            RectF r1 = new RectF();
            r1.left = 0;
            r1.right = getWidth();
            r1.top = 0;
            r1.bottom = getWidth();
            canvas.drawRoundRect(r1, 5, 5, mPaintBackground);
            // 写字
            mPaintText.setColor(Color.WHITE);
            // 两个文字，体贴相应做小
            int len = text.toCharArray().length;
            if (len >= 2) {
                isMoreText = true;
            } else {
                isMoreText = false;
            }
            if (isMoreText) {
                mPaintText.setTextSize(getWidth() / 3);
            } else {
                mPaintText.setTextSize(getWidth() / 2);
            }
            mPaintText.setStrokeWidth(3);
            mPaintText.getTextBounds(text, 0, 1, mRect);
            // 垂直居中
            Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt();
            int baseline = (getMeasuredHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
            // 左右居中
            mPaintText.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, getWidth() / 2, baseline, mPaintText);
        }
    }

    /**
     * @param content 传入字符内容 只会取内容的第一个字符,如果是字母转换成大写
     */
    public void setText(String content) {

        if (content == null) {
            content = " ";
        }
        if (isContainChinese(content)) {
            this.text = getPureChinese(content);

        } else {
            this.text = String.valueOf(content.toCharArray()[0]);

            charHash = this.text.hashCode();
            // 重绘
            invalidate();
        }
        this.text = text.toUpperCase();
    }

    /**
     * 设置头像颜色
     *
     * @param color 头像颜色
     */
    public void setColor(int color) {
        this.color = color;
        // weiqiang invalidate();可以不重绘，再setText进行重绘，不然每次对于一个头像都要重绘两次
    }

    /*
     * weiqiang 替换字符传中全部非中文为null，并仅返回最后两个中文，类似叮叮的头像显示
     */
    private String getPureChinese(String content) {
        String reg = "[^\u4e00-\u9fa5]";
        content = content.replaceAll(reg, "");
        int len = content.toCharArray().length;
        if (len >= 2) {
            return content.substring(len - 2);
        }
        return content;
    }

    /*
     * weiqiang 判断一个字符串中是否含有中文
     */
    private boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
