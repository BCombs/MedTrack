package com.billcombsdevelopment.medtrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.billcombsdevelopment.medtrack.ui.MedListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MedListFragment.newInstance())
                    .commitNow();
        }
    }
}
