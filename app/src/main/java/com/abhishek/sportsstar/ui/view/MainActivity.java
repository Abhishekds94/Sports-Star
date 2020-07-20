package com.abhishek.sportsstar.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.abhishek.sportsstar.R;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To hide the title bar in the activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        //Initialize Flurry Analytics SDK
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "SJDRG2JFRWMSVSJ9QGFV");
        FlurryAgent.logEvent("App_Open");

        //To check active internet on the device
        checkInternetAvailibility();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_schedule:
                        ResultFragment resultFragment = ResultFragment.getInstance();
                        openFragment(resultFragment);
                        FlurryAgent.logEvent("ScheduleFragment");
                        Bundle schedule = new Bundle();
                        schedule.putString(FirebaseAnalytics.Param.CONTENT, "ScheduleFragment");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, schedule);
                        break;
                    case R.id.action_search:
                        SearchFragment searchFragment = SearchFragment.getInstance();
                        openFragment(searchFragment);
                        FlurryAgent.logEvent("SearchFragment");
                        Bundle search = new Bundle();
                        search.putString(FirebaseAnalytics.Param.CONTENT, "SearchFragment");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, search);
                        break;
                    case R.id.action_fixtures:
                        FixtureFragment fixtureFragment = FixtureFragment.getInstance();
                        openFragment(fixtureFragment);
                        FlurryAgent.logEvent("FixturesFragment");
                        Bundle fixtures = new Bundle();
                        fixtures.putString(FirebaseAnalytics.Param.CONTENT, "FixturesFragment");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, fixtures);
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_schedule);

        //Load the AdView
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    /**
     * Open fragment
     *
     * @param fragment instance of base fragment
     */
    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }


    //To check active internet connection
    public void checkInternetAvailibility() {
        if (isInternetAvailable()) {
            Log.e("Yes", "Yes");
        } else {
            Intent intent = new Intent(MainActivity.this, NoInternet.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean isInternetAvailable() {
        try {

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {

            Log.e("isInternetAvailable:", e.toString());
            return false;
        }
    }

    class IsInternetActive extends AsyncTask<Void, Void, String> {

        InputStream is = null;
        String json = "Fail";

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL strUrl = new URL("http://icons.iconarchive.com/icons/designbolts/handstitch-social/24/Android-icon.png");
                //Icon for testing the server

                URLConnection connection = strUrl.openConnection();
                connection.setDoOutput(true);
                is = connection.getInputStream();
                json = "Success";

            } catch (Exception e) {
                e.printStackTrace();
                json = "Fail";
            }
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.equals("Fail")) {
                    Log.e("Internet Not Active", "Internet Not Active");
                } else {
                    Log.e("Internet Active", "Internet Active" + result);
                }
            } else {
                Log.e("Internet Not Active", "Internet Not Active");
            }
        }

        @Override
        protected void onPreExecute() {
            Log.e("Validating", "Validating Internet");
            super.onPreExecute();
        }

    }


}