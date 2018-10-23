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
        retrieveMedList();
    }

    /**
     * Initializes the RecyclerView, LayoutManager, and Adapter
     */
    private void initRecyclerView() {

        mMedListRv.setHasFixedSize(true);
        // Set up the LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mMedListRv.setLayoutManager(mLayoutManager);

        // Set up the Adapter
        mAdapter = new MedListRecyclerViewAdapter();
        mMedListRv.setAdapter(mAdapter);
    }

    /**
     * Uses the ViewModel to retrieve data and creates an observer on the LiveData so
     * when the medicine list is changed, the RecyclerView Adapter's data is updated
     */
    private void retrieveMedList() {

        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        mViewModel.getMedList().observe(this, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(@Nullable List<Medicine> medicines) {
                mAdapter.updateData(medicines);
            }
        });
    }
}
