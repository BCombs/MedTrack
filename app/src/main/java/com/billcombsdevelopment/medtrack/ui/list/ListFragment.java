package com.billcombsdevelopment.medtrack.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.list.adapters.MedListRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    @BindView(R.id.medlist_rv)
    RecyclerView mMedListRv;
    private ListViewModel mViewModel;
    private MedListRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        // Create the observer
        final Observer<List<Medicine>> medicineObserver = new Observer<List<Medicine>>() {
            @Override
            public void onChanged(@Nullable List<Medicine> medList) {
                mAdapter.updateData(medList);
            }
        };

        // Start observing
        mViewModel.getMedList().observe(this, medicineObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();
        mViewModel.insertTestData();
    }

    private void initRecyclerView() {

        mMedListRv.setHasFixedSize(true);
        // Set up the LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mMedListRv.setLayoutManager(mLayoutManager);

        // Set up the Adapter
        mAdapter = new MedListRecyclerViewAdapter();
        mMedListRv.setAdapter(mAdapter);
    }
}
