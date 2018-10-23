package com.billcombsdevelopment.medtrack.ui.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.repository.MedRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private LiveData<List<Medicine>> medList;
    private MedRepository medRepo;

    public ListViewModel(Application application) {
        super(application);
        medRepo = new MedRepository(getApplication());
    }

    /**
     * Checks to see if the list is null, if it is, instantiate it and
     *
     * @return LiveData<List   <   Medicine>> The list of medications currently in the database
     */
    public LiveData<List<Medicine>> getMedList() {
        if (medList == null) {
            medList = medRepo.getMedList();
        }
        return medList;
    }
}