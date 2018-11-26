/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.model.User;
import com.billcombsdevelopment.medtrack.repository.MedRepository;

import java.util.List;

public class MedViewModel extends AndroidViewModel {

    private LiveData<List<Medicine>> medList;
    private MedRepository medRepo;
    private User mCurrentUser;
    private SharedPreferences mSharedPref = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
    private SharedPreferences.Editor spEditor;

    public MedViewModel(Application application) {
        super(application);
        medRepo = new MedRepository(getApplication());

        // If there is not a user ID already created, create it.
        if(!mSharedPref.contains("user_id")) {
            spEditor.putInt("user_id", 0);
        }
    }

    /**
     * Checks to see if the list is null, if it is, instantiate it and
     *
     * @return medList - The list of medications currently in the database
     */
    public LiveData<List<Medicine>> getMedList() {
        if (medList == null) {
            medList = new MutableLiveData<>();
            medList = medRepo.getMedList();
        }
        return medList;
    }

    /**
     * Return a specific medication stored in the medList
     *
     * @param position - position of the medication in the list
     * @return - The medicine object at the position in the list
     */
    public Medicine getMedicine(int position) {
        return medList.getValue().get(position);
    }

    /**
     * Call the repository to insert medication to database
     *
     * @param med - the medication to be added
     */
    public void insertMedication(Medicine med) {
        medRepo.insertMedication(med);
    }

    /**
     * Call to repository to update medication in the database
     *
     * @param med - medication to be updated
     */
    public void updateMedication(Medicine med) {
        medRepo.updateMedication(med);
    }

    /**
     * Call to repository to delete a medication in the database
     * @param id
     */
    public void deleteMedication(int id) {
        medRepo.deleteMedication(id);
    }

    public String getCurrentUser() {
        return mCurrentUser.getUserName();
    }
}