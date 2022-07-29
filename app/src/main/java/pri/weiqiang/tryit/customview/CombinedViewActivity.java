package pri.weiqiang.tryit.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class CombinedViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_view);
        CombinedView combinedView = findViewById(R.id.combined_view);
        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(v -> {
            combinedView.setTvInfo("Update this view");
            combinedView.setTvProgress("combinedView.setTvProgress");
            combinedView.setUse(true);
        });
    }

}
