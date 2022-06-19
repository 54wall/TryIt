package pri.weiqiang.tryit.customview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class ViewStubActivity extends BaseActivity {

    private ViewStub stubView;
    private View emptyView;
    private ListView listView;
    private boolean isUdpate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        stubView = findViewById(R.id.emptyView);
        listView = findViewById(R.id.lv_kid_online);
        Button btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(v -> {
            if (isUdpate) {
                showErrorView();
            } else {
                hintErrorView();
            }
            isUdpate = !isUdpate;
        });
    }

    //显示数据空的效果
    private void showErrorView() {
        if (emptyView == null) {
            //ViewStub 只能加载一次
            emptyView = stubView.inflate();
            TextView text = findViewById(R.id.empty_text);
            text.setText("当前园所没有设置摄像头，请联系园所设置");
        }
        emptyView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    //隐藏数据空的效果
    private void hintErrorView() {
        if (emptyView == null) {
            emptyView = stubView.inflate();
        }
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }


}
