package com.billcombsdevelopment.medtrack.ui;

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
import android.widget.TextView;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.adapters.MedListRecyclerViewAdapter;
import com.billcombsdevelopment.medtrack.ui.viewmodels.ListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedListFragment extends Fragment {

    @BindView(R.id.medlist_rv)
    RecyclerView mMedListRv;
    @BindView(R.id.no_meds_tv)
    TextView mNoMedsTv;
    private ListViewModel mViewModel;
    private MedListRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static MedListFragment newInstance() {
        return new MedListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.med_list_fragment, container, false);
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
        intiViewModel();
        //mViewModel.insertTestData();
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
     * Initializes the ViewModel and creates an Observer for the LiveData
     */
    private void intiViewModel() {

        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        mViewModel.getMedList().observe(this, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(@Nullable List<Medicine> medicines) {
                if (medicines == null || medicines.isEmpty()) {
                    mNoMedsTv.setVisibility(View.VISIBLE);
                    mMedListRv.setVisibility(View.GONE);
                } else {
                    // The list is not empty. If the 'no meds' text view is visible, hide it.
                    if (mNoMedsTv.getVisibility() == View.VISIBLE) {
                        mMedListRv.setVisibility(View.VISIBLE);
                        mNoMedsTv.setVisibility(View.GONE);
                    }
                    mAdapter.updateData(medicines);
                }

            }
        });
    }
}