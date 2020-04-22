package pri.weiqiang.tryit.textview;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-9-6
 * Time: 下午2:53
 */
public interface OnScrollChangedListener {

    /**
     * 监听滚动变化
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    void onScrollChanged(int l, int t, int oldl, int oldt);

    /**
     * 监听滚动到顶部
     */
    void onScrollTop();

    /**
     * 监听滚动到底部
     */
    void onScrollBottom();

}
