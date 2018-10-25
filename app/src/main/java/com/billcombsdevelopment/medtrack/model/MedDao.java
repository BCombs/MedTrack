/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedDao {
    @Query("SELECT * FROM medicine")
    LiveData<List<Medicine>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicine(Medicine medicine);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMedicine(Medicine medicine);

    @Delete
    void delete(Medicine medicine);

    @Query("SELECT * FROM medicine WHERE mId = :id")
    LiveData<Medicine> loadMedById(int id);
}
