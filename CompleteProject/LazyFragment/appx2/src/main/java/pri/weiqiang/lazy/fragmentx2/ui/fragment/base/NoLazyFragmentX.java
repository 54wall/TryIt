package pri.weiqiang.lazy.fragmentx2.ui.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class NoLazyFragmentX extends Fragment {
    private final String TAG = NoLazyFragmentX.class.getSimpleName();

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
    private final boolean currentVisibleStatus = false;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "name:" + this.getArguments().get("name") + "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "name:" + this.getArguments().get("name") + "onCreate");
    }

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

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Log.i(TAG, "==>setUserVisibleHint");
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onActivityCreated");
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
//        if (currentVisibleStatus) {
//            dispatchVisibleStatus(false);
//        } else {
//            dispatchVisibleStatus(true);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onPause");
//        if (currentVisibleStatus) {
//            dispatchVisibleStatus(false);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "name:" + this.getArguments().get("name") + "==>onDetach");
    }
//    public void dispatchVisibleStatus(boolean visibleStatus) {
//        this.currentVisibleStatus = visibleStatus;
//        if (visibleStatus) {
//            onStartLoad();
//        } else {
//            onStopLoad();
//        }
//    }

//    public void onStartLoad() {
//        Log.i(TAG, "name:"+this.getArguments().get("name")  + "==>onStartLoad加载数据");
//    }
//
//    public void onStopLoad() {
//        Log.i(TAG, "name:"+this.getArguments().get("name")  + "==>onStopLoad暂停加载");
//    }
}

