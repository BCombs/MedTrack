/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.repository.MedRepository;

import java.util.List;

public class MedViewModel extends AndroidViewModel {

    private LiveData<List<Medicine>> medList;
    private MedRepository medRepo;

    public MedViewModel(Application application) {
        super(application);
        medRepo = new MedRepository(getApplication());
    }

    /**
     * Checks to see if the list is null, if it is, instantiate it and
     *
     * @return LiveData<List   <   Medicine>> medList - The list of medications currently in the database
     */
    public LiveData<List<Medicine>> getMedList() {
        if (medList == null) {
            medList = new MutableLiveData<>();
            medList = medRepo.getMedList();
        }
        return medList;
    }

    public Medicine getMedicine(int position) {
        Medicine medicine = medList.getValue().get(position);
        return medicine;
    }

    public void insertMedication(Medicine med) {
        new AddMedAsyncTask(medRepo).execute(med);
    }

    private static class AddMedAsyncTask extends AsyncTask<Medicine, Void, Void> {

        private MedRepository mMedRepo;

        AddMedAsyncTask(MedRepository repo) {
            mMedRepo = repo;
        }

        @Override
        protected Void doInBackground(Medicine... medicines) {
            mMedRepo.insertMedication(medicines[0]);
            return null;
        }
    }
}