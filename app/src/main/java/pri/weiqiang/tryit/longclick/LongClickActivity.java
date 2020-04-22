package pri.weiqiang.tryit.longclick;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class LongClickActivity extends BaseActivity {
    private String TAG = LongClickActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_click);
        Button mBtnLongClick = findViewById(R.id.btn_long_click);
        mBtnLongClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "onLongClick");
                return false;
            }
        });
        Button mBtnCustomLongClick = findViewById(R.id.btn_custom_long_click);
//        mBtnCustomLongClick.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.e(TAG, "onLongClick");
//                return false;
//            }
//        });

        LongClickUtils.setLongClick(new Handler(), mBtnCustomLongClick, 4000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "自定义长按onLongClick");
                //todo:补充长按事件的处理逻辑
                return true;
            }
        });
    }
}
