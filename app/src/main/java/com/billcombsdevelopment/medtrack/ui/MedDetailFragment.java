/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billcombsdevelopment.medtrack.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedDetailFragment extends Fragment {


    public MedDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_med_detail, container, false);
    }

}
