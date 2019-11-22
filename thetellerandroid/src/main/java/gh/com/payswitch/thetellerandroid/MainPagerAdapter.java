package gh.com.payswitch.thetellerandroid;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainPagerAdapter extends FragmentPagerAdapter {

    List<thetellerFragment> fragments = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    public List<thetellerFragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<thetellerFragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}