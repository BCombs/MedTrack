/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.billcombsdevelopment.medtrack.model.AppDatabase;
import com.billcombsdevelopment.medtrack.model.Medicine;

import java.util.List;

public class MedRepository {
    private AppDatabase mDb;

    public MedRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public LiveData<List<Medicine>> getMedList() {
        return mDb.medDao().getAll();
    }

    public void insertTestData(Medicine medicine) {
        mDb.medDao().insertMedicine(medicine);
    }

    public LiveData<Medicine> loadMedById(int id) {
        return mDb.medDao().loadMedById(id);
    }
}
