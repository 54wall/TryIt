package pri.weiqiang.tryit.seekbar.widget.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 封装了对控件的一些操作的工具类
 *
 * @author DrkCore
 * @since 2015年11月13日18:47:08
 */
public final class ViewUtil {

    private static float density;

    /*视图参数*/
    private static float scaledDensity;
    private static int widthPixels;
    private static int heightPixels;
    private static boolean isInit = false;

    private ViewUtil() {
    }

    private static void confirmInit() {
        if (!isInit) {
            throw new IllegalStateException("ViewUtil还未初始化");
        }
    }

    public static void init(Context context) {
        if (isInit) {
            return;
        }

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        density = displayMetrics.density;
        scaledDensity = displayMetrics.scaledDensity;
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
        isInit = true;
    }

    public static float getDisplayMetricsDensity() {
        confirmInit();
        return density;
    }

    public static float getDisplayMetricsScaledDensity() {
        confirmInit();
        return scaledDensity;
    }

    public static int getScreenWidthPx() {
        confirmInit();
        return widthPixels;
    }

    public static int getScreenHeightPx() {
        confirmInit();
        return heightPixels;
    }

    /* 单位转换 */

    public static int dpToPx(float dpValue) {
        confirmInit();
        return (int) (dpValue * getDisplayMetricsDensity() + 0.5F);
    }

    public static int pxToDp(float pxValue) {
        confirmInit();
        return (int) (pxValue / getDisplayMetricsDensity() + 0.5F);
    }

    public static int pxToSp(float pxValue) {
        confirmInit();
        return (int) (pxValue / getDisplayMetricsScaledDensity() + 0.5f);
    }

    public static int spToPx(float spValue) {
        confirmInit();
        return (int) (spValue * getDisplayMetricsScaledDensity() + 0.5f);
    }
}
