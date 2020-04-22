package pri.weiqiang.tryit.fronts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * @author 54wall
 * @version 1.0
 * <p>
 * 在MyApplication中需要对Typeface进行反射设置，Activity也必须使用原始主题android:Theme.Light
 * @date 创建时间：2016-7-28 下午2:20:59
 */
public class FrontActivity extends BaseActivity {
    public TextView textView01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        textView01 = super.findViewById(R.id.tv_two);
        textView01.setTypeface(null, Typeface.NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
