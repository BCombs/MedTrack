/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

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

    public void insertMedication(Medicine medicine) {
        new AddMedAsyncTask(mDb).execute(medicine);
    }

    public void updateMedication(Medicine medicine) {
        new UpdateMedAsyncTask(mDb).execute(medicine);
    }

    public LiveData<Medicine> loadMedById(int id) {
        return mDb.medDao().loadMedById(id);
    }

    /**
     * AsyncTask to handle insertion into the database
     */
    private static class AddMedAsyncTask extends AsyncTask<Medicine, Void, Void> {

        private AppDatabase mDb;

        AddMedAsyncTask(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(Medicine... medicines) {
            mDb.medDao().insertMedicine(medicines[0]);
            return null;
        }
    }

    /**
     * AsyncTask to handle updates in the database
     */
    private static class UpdateMedAsyncTask extends AsyncTask<Medicine, Void, Void> {
        private AppDatabase mDb;

        UpdateMedAsyncTask(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(Medicine... medicines) {
            mDb.medDao().updateMedicine(medicines[0]);
            return null;
        }
    }
}
