/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "medicine", indices = {@Index(value = {"med_name"}, unique = true)})
public class Medicine implements Parcelable {
    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel source) {
            return new Medicine(source);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "med_id")
    private int mId;
    @ColumnInfo(name = "med_name")
    private String mName;
    @ColumnInfo(name = "dosage")
    private String mDose;
    @ColumnInfo(name = "directions")
    private String mDirections;
    @ColumnInfo(name = "user_id")
    private int mUserId;

    public Medicine(String name, String dose, String directions, int userId) {
        this.mName = name;
        this.mDose = dose;
        this.mDirections = directions;
        this.mUserId = userId;
    }

    protected Medicine(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mDose = in.readString();
        this.mDirections = in.readString();
        this.mUserId = in.readInt();
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

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mDose);
        dest.writeString(this.mDirections);
        dest.writeInt(this.mUserId);
    }
}
