/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "medicine", indices = {@Index(value = {"med_name"}, unique = true)})
public class Medicine {
    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "med_name")
    private String mName;
    @ColumnInfo(name = "dosage")
    private String mDose;
    @ColumnInfo(name = "directions")
    private String mDirections;
    @ColumnInfo(name = "frequency")
    private int mFrequency;
    @ColumnInfo(name = "dosage_interval")
    private String mDosageInterval;

    public Medicine(String name, String dose, String directions, int frequency, String dosageInterval) {
        mName = name;
        mDose = dose;
        mDirections = directions;
        mFrequency = frequency;
        mDosageInterval = dosageInterval;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDose() {
        return mDose;
    }

    public void setDose(String mDose) {
        this.mDose = mDose;
    }

    public String getDirections() {
        return mDirections;
    }

    public void setDirections(String mDirections) {
        this.mDirections = mDirections;
    }

    public int getFrequency() {
        return mFrequency;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }

    public String getDosageInterval() {
        return mDosageInterval;
    }

    public void setDosageInterval(String mDosageInterval) {
        this.mDosageInterval = mDosageInterval;
    }
}
