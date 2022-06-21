package pri.weiqiang.lazy.fragment.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = null;

    public MyFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
