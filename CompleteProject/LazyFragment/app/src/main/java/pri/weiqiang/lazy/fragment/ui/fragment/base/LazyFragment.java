package pri.weiqiang.lazy.fragment.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author chenke
 * @create 2021/2/25
 * @Describe
 *
 * https://www.jianshu.com/p/4e157a574d6e
 */
public abstract class LazyFragment extends Fragment {
   private final String TAG = "lazy_fragment";
   private View mRootView;

   private boolean viewCreated = false;

   private boolean currentVisibleStatus = false;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      if (mRootView == null) {
         mRootView = inflater.inflate(getLayout(), container, false);
      }
      initView(mRootView);
      viewCreated = true;
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "====>onCreateView");
      if (getUserVisibleHint()) {
         setUserVisibleHint(true);
      }
      return mRootView;
   }

   public abstract int getLayout();

   public abstract void initView(View view);

   @Override
   public void setUserVisibleHint(boolean isVisibleToUser) {
      super.setUserVisibleHint(isVisibleToUser);
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "====>setUserVisibleHint");
      if (viewCreated) {
         if (!currentVisibleStatus && isVisibleToUser) {
            dispatchUserVisibleStatus(true);
         } else if (currentVisibleStatus && !isVisibleToUser) {
            dispatchUserVisibleStatus(false);
         }
      }
   }

   public void dispatchUserVisibleStatus(boolean isUserVisibleStatus) {
      currentVisibleStatus = isUserVisibleStatus;
      if (isUserVisibleStatus) {
         onStartLoad();
      } else {
         onStopLoad();
      }
      //在嵌套模式下，让子类的fragment进行分发
      FragmentManager fm = getChildFragmentManager();
      List<Fragment> fragments = fm.getFragments();
      if (fragments.size() > 0) {
         for (Fragment fragment : fragments) {
            if (fragment instanceof LazyFragment) {
               if (fragment.getUserVisibleHint()) {
                  ((LazyFragment) fragment).dispatchUserVisibleStatus(true);
               }
            }
         }
      }
   }

   @Override
   public void onResume() {
      super.onResume();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onResume");
      if (getUserVisibleHint() && !currentVisibleStatus) {
         dispatchUserVisibleStatus(true);
      }
   }

   @Override
   public void onPause() {
      super.onPause();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onPause");
      if (getUserVisibleHint() && currentVisibleStatus) {
         dispatchUserVisibleStatus(false);
      }
   }

   @Override
   public void onDetach() {
      super.onDetach();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onDetach");
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onDestroyView");
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onDestroy");
   }

   /**
    * 子类重写该方法来实现开始加载数据
    */
   public void onStartLoad() {
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "====>开始加载数据onStartLoad");
   }

   /**
    * 子类重写该方法来实现暂停数据加载
    */
   public void onStopLoad() {
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "====>停止加载数据onStopLoad");
   }
}


