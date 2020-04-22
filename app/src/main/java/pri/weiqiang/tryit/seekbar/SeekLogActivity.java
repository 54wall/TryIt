package pri.weiqiang.tryit.seekbar;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class SeekLogActivity extends BaseActivity {
    ArrayList<String> logList;
    RecyclerView rv;
    LogAdapter logAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_log);
        rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        logList = new ArrayList<>();
        for (int j = 0; j < 1000 * 2; j++) {
            logList.add(j + "测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据");
        }
        logAdapter = new LogAdapter(logList);
        rv.setAdapter(logAdapter);
        rv.getLayoutManager().setAutoMeasureEnabled(false);
        logAdapter.notifyDataSetChanged();
        SeekBar seekBar = findViewById(R.id.seekBar_main_scrollThumb);
        NestedScrollView scrollView = findViewById(R.id.nestedScrollView_frag_edit);
//        ScrollBindHelper.bind(seekBar, scrollView);
    }
}
