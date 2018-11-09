package com.billcombsdevelopment.medtrack;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.billcombsdevelopment.medtrack.ui.MedListFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    // Using test APP ID - Replace with actual AP ID
    private final String APP_ID = "ca-app-pub-3940256099942544~3347511713";
    private FirebaseAnalytics mFireBaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {

            // Get instance of FirebaseAnalytics
            mFireBaseAnalytics = FirebaseAnalytics.getInstance(this);

            // AdMob
            MobileAds.initialize(this, APP_ID);

            // Attach the medication list fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MedListFragment.newInstance())
                    .commitNow();
        }

        // Determine if the up button needs to be displayed
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackCount = getSupportFragmentManager().getBackStackEntryCount();
                if (stackCount > 0) {
                    // Up button needs to be displayed
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setHomeButtonEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                } else {
                    // Don't display up button
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setHomeButtonEnabled(false);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
