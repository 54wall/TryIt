package pri.weiqiang.lazy.fragment.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pri.weiqiang.lazy.fragment.R;
import pri.weiqiang.lazy.fragment.ui.fragment.base.NoLazyFragment;

public class CommonFragment extends NoLazyFragment {
    public static final String TAG = CommonFragment.class.getSimpleName();
    private String name;
    private TextView tvName;
    private TextView tvInfo;

    public static CommonFragment newInstance(String name) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle(1);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_common, container, false);
//    }

    @Override
    public int getLayout() {
        return R.layout.fragment_common;
    }

    @Override
    public void initView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvInfo = view.findViewById(R.id.tv_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        name = getArguments().getString("name");
        tvName.setText(name);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getInfo();
    }

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
                        tvInfo.setText("init Success:"+CommonFragment.this.getArguments().get("name"));
                    }
                });

            }
        }).start();
    }
}
