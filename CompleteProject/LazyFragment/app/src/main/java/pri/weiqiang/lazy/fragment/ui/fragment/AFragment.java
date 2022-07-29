package pri.weiqiang.lazy.fragment.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pri.weiqiang.lazy.fragment.R;
import pri.weiqiang.lazy.fragment.ui.fragment.base.LazyFragment;

public class AFragment extends LazyFragment {
    private String TAG = AFragment.class.getSimpleName();
    private TextView tvInfo;
    @Override
    public int getLayout() {
        return R.layout.fragment_a;
    }

    @Override
    public void initView(View view) {
         tvInfo = view.findViewById(R.id.tv_info);
    }

    @Override
    public void onStartLoad(){
        Log.e(TAG,"onStartLoad()");
        tvInfo.setText("onStartLoad()");
    }

    @Override
    public void onStopLoad(){
        Log.e(TAG,"onStopLoad");
    }
}
