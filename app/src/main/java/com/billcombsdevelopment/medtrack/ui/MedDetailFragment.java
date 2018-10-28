/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.viewmodels.MedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedDetailFragment extends Fragment {

    @BindView(R.id.med_detail_name_tv)
    TextView mMedNameTv;
    @BindView(R.id._med_detail_dose_tv)
    TextView mMedDoseTv;
    @BindView(R.id.med_directions_detail_content_tv)
    TextView mMedDirectionsTv;
    @BindView(R.id.edit_btn)
    Button mEditBtn;
    @BindView(R.id.delete_btn)
    Button mDeleteBtn;

    private MedViewModel mViewModel;
    private Medicine mMedicine;
    private int mPosition;


    public MedDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_med_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(getActivity()).get(MedViewModel.class);

        if (getArguments() != null && getArguments().containsKey("position")) {
            mPosition = getArguments().getInt("position");
            mMedicine = mViewModel.getMedicine(mPosition);
        }

        displayMedDetails();

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putInt("position", mPosition);
                EditMedFragment editMedFragment = new EditMedFragment();
                editMedFragment.setArguments(args);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.addToBackStack("medDetail");
                transaction.replace(R.id.container, editMedFragment).commit();
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id = mMedicine.getId();

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(getResources().getString(R.string.confirm_delete_title));
                String alertMessage = getResources()
                        .getString(R.string.confirm_delete_message, mMedicine.getName());
                alertDialog.setMessage(alertMessage);
                alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewModel.deleteMedication(id);

                        // Alert the user know the medication was deleted
                        Toast.makeText(getActivity(),
                                getResources().getString(R.string.delete_success, mMedicine.getName()),
                                Toast.LENGTH_SHORT).show();

                        // Remove the fragment since the medication doesn't exist any more
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                });
                alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close and do nothing
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
            }
        });

    }

    private void displayMedDetails() {

        if (mMedicine != null) {
            mMedNameTv.setText(mMedicine.getName());
            mMedDoseTv.setText(mMedicine.getDose());
            mMedDirectionsTv.setText(mMedicine.getDirections());
        } else {
            // There was a problem retrieving the medication
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.error_loading_details), Toast.LENGTH_SHORT);
        }
    }
}
