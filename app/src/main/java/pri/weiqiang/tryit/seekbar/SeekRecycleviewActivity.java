package pri.weiqiang.tryit.seekbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.seekbar.widget.RecyclerViewBindHelper;

public class SeekRecycleviewActivity extends BaseActivity {
    ArrayList<String> logList;
    RecyclerView rv;
    LogAdapter logAdapter;
    SeekBar seekBar;
    private String TAG = SeekRecycleviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate()");
        setContentView(R.layout.activity_seek_recycleview);
        rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        logList = new ArrayList<>();
        for (int j = 0; j < 10000; j++) {
            logList.add("--------------------------------------第" + j + "条测试诗句数据--------------------------------------");
        }
        logAdapter = new LogAdapter(logList);
        rv.setAdapter(logAdapter);
        rv.scrollToPosition(logList.size() - 1);
        seekBar = findViewById(R.id.seekBar_main_scrollThumb);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logList.clear();
                /*logList 更新后，adapter必须进行notifyDataSetChanged，否则报错
                java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionVie*/
                logAdapter.notifyDataSetChanged();
            }
        });
        RecyclerViewBindHelper.bind(seekBar, rv, true);


    }
}
