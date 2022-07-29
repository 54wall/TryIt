package pri.weiqiang.lazy.fragmentx2.ui.activity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import pri.weiqiang.lazy.fragmentx2.R;
import pri.weiqiang.lazy.fragmentx2.ui.adapter.MyFragmentPagerAdapter;
import pri.weiqiang.lazy.fragmentx2.ui.fragment.OneFragment;

/**
 * ViewPager2中的Fragment懒加载实现方式
 * https://www.jianshu.com/p/1d95e729c571
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager2 vp2;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fragments.add(OneFragment.newInstance("OneFragment-" + i));
        }
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(this, fragments);
        vp2 = findViewById(R.id.view_pager2);
        vp2.setAdapter(myFragmentPagerAdapter);
        //根据源码注解，离屏页面缓存指的是两侧
        vp2.setOffscreenPageLimit(1);
    }
}