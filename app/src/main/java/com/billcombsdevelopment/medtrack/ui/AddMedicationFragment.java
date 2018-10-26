/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.viewmodels.MedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicationFragment extends Fragment {

    @BindView(R.id.med_name_et)
    EditText mMedNameEditText;
    @BindView(R.id.med_dose_et)
    EditText mDoseEditText;
    @BindView(R.id.med_instructions_et)
    EditText mMedInstructionsEditText;

    @BindView(R.id.num_doses_spinner)
    Spinner mNumDosesSpinner;
    @BindView(R.id.dose_interval_spinner)
    Spinner mDoseIntervalSpinner;

    @BindView(R.id.add_medication_btn)
    Button mAddMedButton;

    private String mMedName;
    private String mMedDose;
    private String mInstructions;
    private int mNumDoses;
    private String mDoseInterval;
    private MedViewModel mMedViewModel;

    public AddMedicationFragment() {
        // Required empty public constructor
    }

    public static AddMedicationFragment newInstance() {
        return new AddMedicationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_medication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mMedViewModel = ViewModelProviders.of(getActivity()).get(MedViewModel.class);

        mAddMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check to make sure all fields have been filled out
                if (mMedNameEditText.getText().toString().isEmpty() ||
                        mDoseEditText.getText().toString().isEmpty() ||
                        mMedInstructionsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {

                    Medicine medicine = parseMedData();
                    mMedViewModel.insertMedication(medicine);
                }
            }
        });
    }

    private Medicine parseMedData() {
        mMedName = mMedNameEditText.getText().toString();
        mMedDose = mDoseEditText.getText().toString();
        mInstructions = mMedInstructionsEditText.getText().toString();

        // Get the number of doses selected from the spinner
        int doseIntervalPos = mNumDosesSpinner.getSelectedItemPosition();
        String[] values = getResources().getStringArray(R.array.num_doses);
        mNumDoses = Integer.valueOf(values[doseIntervalPos]);

        // Get the dosage interval selected from the spinner
        mDoseInterval = mDoseIntervalSpinner.getSelectedItem().toString();

        return new Medicine(mMedName, mMedDose, mInstructions, mNumDoses, mDoseInterval);
    }
}
