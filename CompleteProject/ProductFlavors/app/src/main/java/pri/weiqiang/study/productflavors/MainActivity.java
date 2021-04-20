package pri.weiqiang.study.productflavors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pri.weiqiang.study.common.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTvInfo = findViewById(R.id.tv_info);
        mTvInfo.setText(Utils.getInfo());
    }
}