/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.billcombsdevelopment.medtrack.model.AppDatabase;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.model.User;

import java.util.List;

public class MedRepository {
    private AppDatabase mDb;

    public MedRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public LiveData<List<Medicine>> getMedList(int userId) {
        return mDb.medDao().getMedList(userId);
    }

    /**
     * Inserts a medication into the database
     *
     * @param medicine - The Medicine object to be inserted
     */
    public void insertMedication(Medicine medicine) {
        new AddMedAsyncTask(mDb).execute(medicine);
    }

    /**
     * Updates a medication already in the database
     *
     * @param medicine - The edited Medicine object
     */
    public void updateMedication(Medicine medicine) {
        new UpdateMedAsyncTask(mDb).execute(medicine);
    }

    /**
     * Deletes a specific medication from the database
     *
     * @param id - The ID of the medication
     */
    public void deleteMedication(int id) {
        new DeleteMedAsyncTask(mDb).execute(id);
    }

    /**
     * Queries the list of users in the database
     *
     * @return LiveData<List   <   User>> the list of users.
     */
    public LiveData<List<User>> getUsers() {
        return mDb.medDao().getUsers();
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

    private static class DeleteMedAsyncTask extends AsyncTask<Integer, Void, Void> {

        private AppDatabase mDb;

        DeleteMedAsyncTask(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mDb.medDao().delete(integers[0]);
            return null;
        }
    }
}
