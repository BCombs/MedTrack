/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.repository.MedRepository;

public class AddMedicationViewModel extends ViewModel {
    private LiveData<Medicine> mMedicine;
    private MedRepository mMedRepo;

    public AddMedicationViewModel (MedRepository repository, int medId) {
        this.mMedRepo = repository;
        mMedicine = mMedRepo.loadMedById(medId);
    }

    public LiveData<Medicine> getMedicine() {
        return mMedicine;
    }
}
