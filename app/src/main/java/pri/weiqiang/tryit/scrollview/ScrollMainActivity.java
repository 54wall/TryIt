package pri.weiqiang.tryit.scrollview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class ScrollMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_main);
        Button mBtnLvScroll = findViewById(R.id.btn_lv_scroll);
        mBtnLvScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(ScrollMainActivity.this, SimpleListViewActivity.class);
                startActivity(i);
            }
        });
        Button mBtnJustRecycleView = findViewById(R.id.btn_just_recycle_view);
        mBtnJustRecycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(ScrollMainActivity.this, SimpleRecycleViewActivity.class);
                startActivity(i);
            }
        });


        Button mBtn2RecycleInScroll = findViewById(R.id.btn_recycle_in_scroll);
        mBtn2RecycleInScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(ScrollMainActivity.this, ScrollViewActivity2.class);
                startActivity(i);
            }
        });

        Button mBtnListActivity = findViewById(R.id.btn_list_activity);
        mBtnListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ScrollMainActivity.this, ListActivity.class);
                startActivity(i);
            }
        });
        Button mBtn2RecycleActivity = findViewById(R.id.btn_recycle_activity);
        mBtn2RecycleActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ScrollMainActivity.this, RecyclerActivity.class);
                startActivity(i);
            }
        });
        Button mBtnNestedScrollview = findViewById(R.id.btn_nested_scrollview);
        mBtnNestedScrollview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ScrollMainActivity.this, NestedScrollViewActivity.class);
                startActivity(i);
            }
        });
    }
}
