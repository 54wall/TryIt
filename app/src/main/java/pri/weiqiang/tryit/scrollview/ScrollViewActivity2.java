package pri.weiqiang.tryit.scrollview;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;


public class ScrollViewActivity2 extends BaseActivity {
    private RecyclerView mRvMain;
    private RecyclerView mRv1;
    private RecyclerView.Adapter mAdapterMain;
    private RecyclerView.Adapter mAdapter1;
    private List<String> mainList = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view2);
        for (int i = 0; i < 100; i++) {
            mainList.add("mainList:" + i);
        }
        for (int i = 0; i < 100; i++) {
            list1.add("list1:" + i);
        }
/*        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapterMain = new MySimpleRecyclerViewAdapter(mainList);
        mRvMain.setItemAnimator(new DefaultItemAnimator());
        mRvMain.setAdapter(mAdapterMain);*/

        mRv1 = findViewById(R.id.rv_1);
        mRv1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter1 = new MySimpleRecyclerViewAdapter(list1);
        mRv1.setItemAnimator(new DefaultItemAnimator());
        mRv1.setAdapter(mAdapter1);

    }
}

