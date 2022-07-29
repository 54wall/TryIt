package pri.weiqiang.tryit.mat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class MatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat);
        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, MatTestActivity.class);
            startActivity(intent);
        });
    }

}
