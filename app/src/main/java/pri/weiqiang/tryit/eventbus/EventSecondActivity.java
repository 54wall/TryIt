package pri.weiqiang.tryit.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * Created by ZZG on 2018/1/10.
 */

public class EventSecondActivity extends BaseActivity {
    private Button mButton2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_second);
        mButton2 = findViewById(R.id.btn2);
        jumpActivity();
    }

    private void jumpActivity() {
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("欢迎大家浏览我写的博客" + System.currentTimeMillis()));
                finish();
            }
        });
    }
}
