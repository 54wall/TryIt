package pri.weiqiang.tryit.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class CustomViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        Button mBtnCircleView = findViewById(R.id.btn_circle_view);
        mBtnCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(CustomViewActivity.this, CircleViewActivity.class);
                startActivity(i);
            }
        });

        Button mBtnVolumneView = findViewById(R.id.btn_volumne_view);
        mBtnVolumneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(CustomViewActivity.this, VolumneViewActivity.class);
                startActivity(i);
            }
        });
    }

}
