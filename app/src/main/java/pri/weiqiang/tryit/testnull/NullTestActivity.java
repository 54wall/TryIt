package pri.weiqiang.tryit.testnull;

import androidx.appcompat.app.AppCompatActivity;
import pri.weiqiang.tryit.R;

import android.os.Bundle;
import android.util.Log;

import java.util.Properties;

public class NullTestActivity extends AppCompatActivity {
    private String TAG = NullTestActivity.class.getSimpleName();
    Properties logPro = new Properties();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_test);

//        Log.e(TAG,"logPro "+logPro.getProperty("TaskNumber"));
        try {
            logPro.setProperty("TaskNumber", null);
        }catch (Exception e){
            Log.e(TAG,"e:"+e.toString());
        }
    }
}
