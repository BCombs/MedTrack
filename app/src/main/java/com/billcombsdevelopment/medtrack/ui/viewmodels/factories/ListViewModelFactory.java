/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui.viewmodels.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.billcombsdevelopment.medtrack.repository.MedRepository;
import com.billcombsdevelopment.medtrack.ui.viewmodels.AddMedicationViewModel;

public class ListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MedRepository mMedRepo;
    private int mMedId;

    public ListViewModelFactory(MedRepository repository, int medId){
        this.mMedRepo = repository;
        this.mMedId = medId;
    }

    @Override
    public <T extends ViewModel> T create (Class<T> modelClass) {
        // noinspection unchecked
        return (T) new AddMedicationViewModel(mMedRepo, mMedId);
    }
}
