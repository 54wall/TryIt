package pri.weiqiang.lazy.fragmentx.ui.fragment.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author chenke
 * @create 2021/2/25
 * @Describe AndroidX中的fragment懒加载
 * <p>
 * https://www.jianshu.com/p/07217bbac5a6
 */
public abstract class LazyFragmentX extends Fragment {
    private final String TAG = LazyFragmentX.class.getSimpleName();

    /**
     * 抽象布局
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * @param view 初始化view
     */
    public abstract void initView(View view);

    private View mRootView;
    /**
     * 当前的activity是否已经可见
     */
    private boolean currentVisibleStatus = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayout(), container, false);
        }
        initView(mRootView);
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onCreateView");
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "==>setUserVisibleHint");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onResume");
        dispatchVisibleStatus(!currentVisibleStatus);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onPause");
        if (currentVisibleStatus) {
            dispatchVisibleStatus(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onStop");
    }

    public void dispatchVisibleStatus(boolean visibleStatus) {
        this.currentVisibleStatus = visibleStatus;
        if (visibleStatus) {
            onStartLoad();
        } else {
            onStopLoad();
        }
    }

    public void onStartLoad() {
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onStartLoad加载数据");
    }

    public void onStopLoad() {
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onStopLoad暂停加载");
    }
}

