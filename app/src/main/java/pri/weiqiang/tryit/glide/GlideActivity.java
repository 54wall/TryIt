package pri.weiqiang.tryit.glide;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class GlideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        final ImageView iv = findViewById(R.id.iv_glide);
        Button mBtnLoad = findViewById(R.id.btn_load);
        mBtnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Glide.with(GlideActivity.this).load(R.drawable.rabbit).asGif().into(iv);
                Glide.with(GlideActivity.this).load(R.drawable.rabbit).into(iv);
            }
        });
    }
}
