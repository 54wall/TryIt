package pri.weiqiang.lazy.fragment.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.lazy.fragment.R;
import pri.weiqiang.lazy.fragment.ui.adapter.MyFragmentStatePagerAdapter;
import pri.weiqiang.lazy.fragment.ui.fragment.CommonFragment;
import pri.weiqiang.lazy.fragment.ui.fragment.OneFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager vp;
    private MyFragmentStatePagerAdapter myFragmentStatePagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<5;i++){
            fragments.add(OneFragment.newInstance("OneFragment-"+i));
        }
        myFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(),fragments);
        vp  =  findViewById(R.id.view_pager);
        vp.setAdapter(myFragmentStatePagerAdapter);
        vp.setOffscreenPageLimit(4);
    }
}