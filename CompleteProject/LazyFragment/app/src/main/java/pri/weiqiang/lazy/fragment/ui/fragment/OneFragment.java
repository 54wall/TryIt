package pri.weiqiang.lazy.fragment.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pri.weiqiang.lazy.fragment.R;
import pri.weiqiang.lazy.fragment.ui.fragment.base.LazyFragment;

public class OneFragment extends LazyFragment {
    private String TAG = OneFragment.class.getSimpleName();
    private TextView tvInfo;


    public static OneFragment newInstance(String name) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle(1);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_one;
    }

    @Override
    public void initView(View view) {
         tvInfo = view.findViewById(R.id.tv_info);
    }

    @Override
    public void onStartLoad(){
        Log.e(TAG,"onStartLoad()");
        getInfo();
    }

    @Override
    public void onStopLoad(){
        Log.e(TAG,"onStopLoad");
    }

    /*延时模仿网络请求*/
    private void getInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvInfo.setText("init Success:"+OneFragment.this.getArguments().get("name"));
                    }
                });

            }
        }).start();
    }
}
