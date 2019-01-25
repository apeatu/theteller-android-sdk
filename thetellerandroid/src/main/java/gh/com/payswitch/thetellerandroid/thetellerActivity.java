package gh.com.payswitch.thetellerandroid;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import gh.com.payswitch.thetellerandroid.card.CardFragment;
import gh.com.payswitch.thetellerandroid.ghmobilemoney.GhMobileMoneyFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.PERMISSIONS_REQUEST_READ_PHONE_STATE;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_PARAMS;

public class thetellerActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;
    RelativeLayout permissionsRequiredLayout;
    View mainContent;
    Button requestPermsBtn;
    int theme;
    thetellerInitializer thetellerInitializer;
    MainPagerAdapter mainPagerAdapter;
    static String apiKey;
    static String apiUser;
    public static String BASE_URL;
    public static int RESULT_SUCCESS = 111;
    public static int RESULT_ERROR = 222;
    public static int RESULT_CANCELLED = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            thetellerInitializer = Parcels.unwrap(getIntent().getParcelableExtra(theteller_PARAMS));
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d(theteller, "Error retrieving initializer");
        }

        theme = thetellerInitializer.getTheme();

        if (theme != 0) {
            try {
                setTheme(theme);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_theteller);

        if (thetellerInitializer.isStaging()) {
            BASE_URL = thetellerConstants.STAGING_URL;
        }
        else {
            BASE_URL = thetellerConstants.LIVE_URL;
        }

        apiKey = thetellerInitializer.getApiKey();
        apiUser = thetellerInitializer.getApiUser();
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        permissionsRequiredLayout = (RelativeLayout) findViewById(R.id.theteller_permission_required_layout);
        mainContent = findViewById(R.id.main_content);
        requestPermsBtn = (Button) findViewById(R.id.requestPermsBtn);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        List<thetellerFragment> thetellerFragments = new ArrayList<>();

        if (thetellerInitializer.isWithCard()) {
            thetellerFragments.add(new thetellerFragment(new CardFragment(), "Card"));
        }

        if (thetellerInitializer.isWithGHMobileMoney()) {
            thetellerFragments.add(new thetellerFragment(new GhMobileMoneyFragment(), "MOBILE MONEY"));
        }

        mainPagerAdapter.setFragments(thetellerFragments);
        pager.setAdapter(mainPagerAdapter);

        tabLayout.setupWithViewPager(pager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForRequiredPermissions();
        }

        requestPermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                            PERMISSIONS_REQUEST_READ_PHONE_STATE);
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mainContent.setVisibility(View.VISIBLE);
                permissionsRequiredLayout.setVisibility(GONE);

            } else {
                permissionsRequiredLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public static String getApiKey() {
        return apiKey;
    }
    public static String getApiUser() {return apiUser;}

    public thetellerInitializer getThetellerInitializer() {
        return thetellerInitializer;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkForRequiredPermissions() {

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsRequiredLayout.setVisibility(View.VISIBLE);
        }
        else {
            permissionsRequiredLayout.setVisibility(GONE);
        }
    }


    private void handleBottomSheetsIfNeeded(){

        String currentFrag = mainPagerAdapter.getPageTitle(pager.getCurrentItem()).toString();

        if (currentFrag.equalsIgnoreCase("card")){
            CardFragment fragment = (CardFragment) mainPagerAdapter.getFragments().get(pager.getCurrentItem()).getFragment();

            if (!fragment.closeBottomSheetsIfOpen()) {
                setResult(thetellerActivity.RESULT_CANCELLED, new Intent());
                super.onBackPressed();
            }
        }
        else {
            setResult(thetellerActivity.RESULT_CANCELLED, new Intent());
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {

        if (mainPagerAdapter == null) {
            mainPagerAdapter = (MainPagerAdapter) pager.getAdapter();
        }

        int size = mainPagerAdapter.getCount();

        if (size > 0) {
            //get the title of the current fragment
            handleBottomSheetsIfNeeded();
        }
        else {
            setResult(thetellerActivity.RESULT_CANCELLED, new Intent());
            super.onBackPressed();
        }
    }
}

