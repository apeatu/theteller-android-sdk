package gh.com.payswitch.thetellerandroid;


import androidx.fragment.app.Fragment;


public class thetellerFragment {
    private Fragment fragment;
    private String title;

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
