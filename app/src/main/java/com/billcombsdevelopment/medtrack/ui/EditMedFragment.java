/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.billcombsdevelopment.medtrack.MainActivity;
import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.viewmodels.MedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A {@link Fragment} subclass for editing an already existing medication.
 */
public class EditMedFragment extends Fragment {

    @BindView(R.id.ud_med_name_et)
    EditText mNameEt;
    @BindView(R.id.ud_med_dose_et)
    EditText mDoseEt;
    @BindView(R.id.ud_med_instructions_et)
    EditText mDirectionsEt;
    @BindView(R.id.ud_num_doses_spinner)
    Spinner mNumDosesSpinner;
    @BindView(R.id.ud_dose_interval_spinner)
    Spinner mDoseIntervalSpinner;
    @BindView(R.id.update_medication_btn)
    Button mUpdateMedBtn;

    private MedViewModel mViewModel;
    private Medicine mMedicine;

    public EditMedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_med, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(MedViewModel.class);
        }

        if (getArguments() != null && getArguments().containsKey("position")) {
            int position = getArguments().getInt("position");
            mMedicine = mViewModel.getMedicine(position);
            populateFields();
        } else {
            // Something went wrong, inform the user
            Toast.makeText(getActivity(),
                    getResources()
                            .getString(R.string.error_loading_details), Toast.LENGTH_SHORT).show();
        }


        // Set the app bar title
        if (mMedicine != null) {
            String title = getResources().getString(R.string.app_name) + " - "
                    + getResources().getString(R.string.edit_appbar_title)
                    + " "
                    + mMedicine.getName();
            if(((MainActivity) getActivity()).getSupportActionBar() != null) {
                ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                actionBar.setTitle(title);
            }
        }

        // Update medication when button is clicked
        mUpdateMedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data entered by the user
                mMedicine.setName(mNameEt.getText().toString());
                mMedicine.setDose(mDoseEt.getText().toString());
                mMedicine.setDirections(mDirectionsEt.getText().toString());

                // Get the number of doses selected from the spinner
                int doseIntervalPos = mNumDosesSpinner.getSelectedItemPosition();
                String[] values = getResources().getStringArray(R.array.num_doses);

                // Update the frequency and intervals of the med
                mMedicine.setFrequency(Integer.valueOf(values[doseIntervalPos]));
                mMedicine.setDosageInterval(mDoseIntervalSpinner.getSelectedItem().toString());

                // Update the med in database
                mViewModel.updateMedication(mMedicine);

                // Alert user medicine was updated
                String updated = getResources().getString(R.string.update_success, mMedicine.getName());
                Toast.makeText(getActivity(), updated, Toast.LENGTH_SHORT).show();

                // Close the fragment
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void populateFields() {
        mNameEt.setText(mMedicine.getName());
        mDoseEt.setText(mMedicine.getDose());
        mDirectionsEt.setText(mMedicine.getDirections());

        mNumDosesSpinner.setSelection(mMedicine.getFrequency() - 1);
        int intervalSpinnerPos = getDoseSpinnerPos();
        mDoseIntervalSpinner.setSelection(intervalSpinnerPos);
    }

    private int getDoseSpinnerPos() {
        switch (mMedicine.getDosageInterval()) {
            case "Hourly":
                return 0;
            case "Daily":
                return 1;
            case "Weekly":
                return 2;
            case "Bi-weekly":
                return 3;
            case "Monthly":
                return 4;
            default:
                return 0;
        }
    }
}
