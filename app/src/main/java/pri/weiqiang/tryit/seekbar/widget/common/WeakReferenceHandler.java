package pri.weiqiang.tryit.seekbar.widget.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * 带有弱引用的Handler子类，具体的回调请参看{@link #onMessageLively(Object, Message, int)}。
 *
 * @param <RefType>
 * @author DrkCore
 * @since 2015年10月7日22:41:44
 */
public class WeakReferenceHandler<RefType> extends Handler {

    private WeakReference<RefType> ref;
    private boolean checkContextEnable = true;

    /* 继承 */


    public WeakReferenceHandler(RefType ref) {
        super();
        this.ref = new WeakReference<>(ref);
    }

    /* 回调 */

    @Override
    public final void handleMessage(Message msg) {
        super.handleMessage(msg);
        RefType realRef = checkAlive(ref);
        if (realRef != null) {
            onMessageLively(realRef, msg, msg.what);
        }
    }

    /* 检查是否未被回收 */

    /**
     * 当执行{@link #handleMessage(Message)}且弱引用指向的对象存在时，回调该方法。
     *
     * @param ref  从弱引用中取出的强引用，当回调时绝对不为null
     * @param msg
     * @param what
     */
    public void onMessageLively(RefType ref, Message msg, int what) {

    }

    /**
     * 是否对{@link #ref}的引用进行生命周期的的检查，检查的过程请参照 {@link #checkAlive(WeakReference)}
     * 。
     * 默认返回true，也就是检查。
     *
     * @return
     */
    protected final boolean isCheckContextEnable() {
        return checkContextEnable;
    }

    public final WeakReferenceHandler setCheckContextEnable(boolean checkContextEnable) {
        this.checkContextEnable = checkContextEnable;
        return this;
    }

    /**
     * 检查{@link #ref}的引用的对象是否可用。
     * 当启用{@link #checkContextEnable}启用时将会检查生命周期是否处于可用的状态，
     * 默认情况下是启用的。
     * 当且仅当引用可用时返回对象，否则为null。
     *
     * @param weakRef
     * @return
     */
    @SuppressLint("NewApi")
    private RefType checkAlive(WeakReference<RefType> weakRef) {
        RefType ref = weakRef.get();
        if (ref != null && isCheckContextEnable()) {
            // 检查activity，Activity是AppCompatActivity和FragmentActivity的父类，因而这些就够了
            if (ref instanceof Activity) {
                Activity act = (Activity) ref;
                if (act.isFinishing() || act.isDestroyed()) {// Activity已经死去
                    ref = null;
                }
            }

            // 检查是否是v4包的frag
            if (ref instanceof Fragment) {
                Fragment frag = (Fragment) ref;
                if (!frag.isAdded()) {//Frag未被添加
                    ref = null;
                }
            }

            // 检查是否属于app包下的frag
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 && ref instanceof android.app.Fragment) {
                android.app.Fragment frag = (android.app.Fragment) ref;
                if (!frag.isAdded()) {//Frag未被添加
                    ref = null;
                }
            }

        }
        return ref;
    }

}
