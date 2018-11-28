/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedDao {
    @Query("SELECT * FROM medicine WHERE user_id = :userId")
    LiveData<List<Medicine>> getMedList(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicine(Medicine medicine);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMedicine(Medicine medicine);

    @Query("DELETE FROM medicine WHERE med_id = :id ")
    void delete(int id);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsers();
}
