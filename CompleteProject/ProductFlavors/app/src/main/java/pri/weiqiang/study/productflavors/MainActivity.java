package pri.weiqiang.study.productflavors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import pri.weiqiang.study.common.Utils;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTvInfo = findViewById(R.id.tv_info);

        String channelName = PackUtils.getChannelName(MainActivity.this);
        Log.i(TAG,"channelName:"+channelName);
        mTvInfo.setText(Utils.getInfo()+" channelName:"+channelName);
    }
}