/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui.list.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedListRecyclerViewAdapter extends
        RecyclerView.Adapter<MedListRecyclerViewAdapter.ViewHolder> {

    private List<Medicine> mMedList;

    public MedListRecyclerViewAdapter() {
        mMedList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medlist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Medicine medicine = mMedList.get(position);
        viewHolder.bind(position, medicine);
    }

    @Override
    public int getItemCount() {
        return mMedList.size();
    }

    public void updateData(List<Medicine> medList) {
        mMedList = medList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.med_name_tv)
        TextView medNameTv;
        @BindView(R.id.med_dosage_tv)
        TextView medDosageTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position, Medicine medicine) {
            medNameTv.setText(medicine.getName());

            String dosage = medicine.getDose() + " " + medicine.getDosageInterval();
            medDosageTv.setText(dosage);
        }
    }
}
