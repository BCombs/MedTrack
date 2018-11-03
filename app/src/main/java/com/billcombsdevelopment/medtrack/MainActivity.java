package com.billcombsdevelopment.medtrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    }
}
