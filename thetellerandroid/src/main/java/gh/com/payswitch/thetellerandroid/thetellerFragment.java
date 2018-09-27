package gh.com.payswitch.thetellerandroid;


import android.support.v4.app.Fragment;


public class thetellerFragment {
    Fragment fragment;
    String title;

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }

    public thetellerFragment(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

}
