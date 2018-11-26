/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui.adapters;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.MedListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedListRecyclerViewAdapter extends
        RecyclerView.Adapter<MedListRecyclerViewAdapter.ViewHolder> {

    MedListFragment.ClickCallback mCallback;
    private List<Medicine> mMedList;

    public MedListRecyclerViewAdapter(MedListFragment.ClickCallback callback) {
        mMedList = new ArrayList();
        mCallback = callback;
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
        @BindView(R.id.med_directions_tv)
        TextView medDirectionsTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position, Medicine medicine) {

            // Set the Medicine name
            medNameTv.setText(medicine.getName());

            // Set the dosage
            medDosageTv.setText(medicine.getDose());

            // Set the directions
            medDirectionsTv.setText(medicine.getDirections());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onClick(position);
                }
            });
        }
    }
}
