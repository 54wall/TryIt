package pri.weiqiang.lazy.fragment.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class NoLazyFragment extends Fragment {
   private final String TAG = NoLazyFragment.class.getSimpleName();
   private View mRootView;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      if (mRootView == null) {
         mRootView = inflater.inflate(getLayout(), container, false);
      }
      initView(mRootView);
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "====>onCreateView");
      return mRootView;
   }

   public abstract int getLayout();

   public abstract void initView(View view);

   @Override
   public void onResume() {
      super.onResume();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onResume");
   }

   @Override
   public void onPause() {
      super.onPause();
      Log.i(TAG, "name:"+this.getArguments().get("name")  + "===>onPause");
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

