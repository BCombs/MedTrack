package com.billcombsdevelopment.medtrack.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billcombsdevelopment.medtrack.MainActivity;
import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.Medicine;
import com.billcombsdevelopment.medtrack.ui.adapters.MedListRecyclerViewAdapter;
import com.billcombsdevelopment.medtrack.ui.viewmodels.MedViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedListFragment extends Fragment {

    @BindView(R.id.medlist_rv)
    RecyclerView mMedListRv;
    @BindView(R.id.no_meds_tv)
    TextView mNoMedsTv;
    @BindView(R.id.list_fab)
    FloatingActionButton mAddMedFab;
    @BindView(R.id.banner_ad)
    AdView mAdView;
    private MedViewModel mViewModel;
    private MedListRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppWidgetManager mAppWidgetManager;

    public static MedListFragment newInstance() {
        return new MedListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_med_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAppWidgetManager = AppWidgetManager.getInstance(getActivity());

        loadAd();
        initRecyclerView();
        intiViewModel();

        // Set the app bar title
        if (getActivity() != null) {
            String title = getResources().getString(R.string.app_name);
            if (((MainActivity) getActivity()).getSupportActionBar() != null) {
                ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                actionBar.setTitle(title);
            }
        }

        mAddMedFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                AddMedicationFragment addMedFragment = new AddMedicationFragment();
                transaction.addToBackStack("list");
                transaction.replace(R.id.container, addMedFragment).commit();
            }
        });
    }

    /**
     * Initializes the RecyclerView, LayoutManager, and Adapter
     */
    private void initRecyclerView() {

        // Customized the divider
        if (getActivity() != null) {
            DividerItemDecoration divider = new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL);
            divider.setDrawable(getActivity().getResources().getDrawable(R.drawable.divider));
            mMedListRv.addItemDecoration(divider);
        }

        mMedListRv.setHasFixedSize(true);
        // Set up the LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mMedListRv.setLayoutManager(mLayoutManager);

        // Set up the Adapter
        mAdapter = new MedListRecyclerViewAdapter(new ClickCallback() {
            @Override
            public void onClick(int position) {
                //pass the position to the med detail fragment
                Bundle args = new Bundle();
                args.putInt("position", position);

                // Replace the fragment with detail fragment passing it the position
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                MedDetailFragment detailFragment = new MedDetailFragment();
                detailFragment.setArguments(args);
                transaction.addToBackStack("list");
                transaction.replace(R.id.container, detailFragment).commit();
            }
        });
        mMedListRv.setAdapter(mAdapter);
    }

    /**
     * Initializes the ViewModel and creates an Observer for the LiveData
     */
    private void intiViewModel() {

        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(MedViewModel.class);
        }
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

    /**
     * Loads an AdMob ad
     */
    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public interface ClickCallback {
        void onClick(int position);
    }
}
