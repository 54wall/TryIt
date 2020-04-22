package pri.weiqiang.tryit.jni;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class JNIActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        Button mBtnHello = findViewById(R.id.btn_hello);
        final TextView mTvHello = findViewById(R.id.tv_hello);
        mBtnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JNIUtils.sayHelloFromJNI();
                mTvHello.setText(JNIUtils.sayHelloFromJNI());
            }
        });
    }

}
