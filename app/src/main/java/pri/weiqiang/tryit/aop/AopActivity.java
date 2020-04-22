package pri.weiqiang.tryit.aop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.aop.aspectj.AspectjActivity;
import pri.weiqiang.tryit.aop.hujiang.PerformanceActivity;

public class AopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop);
        Button mHujiangBtn = findViewById(R.id.btn_hujiang);
        mHujiangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(AopActivity.this, PerformanceActivity.class);
                startActivity(i);
            }
        });

        Button mAspectjBtn = findViewById(R.id.btn_aspectj);
        mAspectjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(AopActivity.this, AspectjActivity.class);
                startActivity(i);
            }
        });

    }
}
