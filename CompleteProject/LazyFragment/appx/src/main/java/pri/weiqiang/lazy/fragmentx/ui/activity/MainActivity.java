package pri.weiqiang.lazy.fragmentx.ui.activity;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import pri.weiqiang.lazy.fragmentx.R;
import pri.weiqiang.lazy.fragmentx.ui.adapter.MyFragmentStatePagerAdapter;
import pri.weiqiang.lazy.fragmentx.ui.fragment.OneFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private MyFragmentStatePagerAdapter myFragmentStatePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fragments.add(OneFragment.newInstance("OneFragment-" + i));
        }
        myFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        vp = findViewById(R.id.view_pager);
        vp.setAdapter(myFragmentStatePagerAdapter);
        vp.setOffscreenPageLimit(4);
    }
}