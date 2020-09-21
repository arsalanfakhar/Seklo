package com.trulyfuture.seklo.screens.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.HrAdapter;
import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.databinding.FragmentHomeBinding;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.JobsResults;
import com.trulyfuture.seklo.utils.ProgressDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements JobsAdapter.OnJobClickListerner {
    private FragmentHomeBinding fragmentHomeBinding;
    private JobsAdapter jobsAdapter;
    private HrAdapter hrAdapter;

    private MainActivityViewModel activityViewModel;

    private static final String TAG = "HomeFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        ProgressDialog.showLoader(getActivity());

        setupViews();
        setupObservers();

    }

    private void setupViews() {
        jobsAdapter = new JobsAdapter(this);
        GridLayoutManager hrGridLayoutManager = new GridLayoutManager(getContext(), 2);
        hrGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentHomeBinding.jobsRV.setLayoutManager(hrGridLayoutManager);
        fragmentHomeBinding.jobsRV.setAdapter(jobsAdapter);

        hrAdapter = new HrAdapter(getContext());
        fragmentHomeBinding.hrRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        fragmentHomeBinding.hrRV.setAdapter(hrAdapter);

        fragmentHomeBinding.viewAllJobsBtn.setOnClickListener(view -> {
            Navigation.findNavController(fragmentHomeBinding.getRoot()).navigate(R.id.action_homeFragment_to_allJobsFragment);
        });

    }

    private void setupObservers() {
        activityViewModel.hrResults.observe(getViewLifecycleOwner(), hrResults -> {
            if (hrResults.getHrList() != null)
                hrAdapter.setHrArrayList((ArrayList<HrResults.Hr>) hrResults.getHrList());
        });


        activityViewModel.homePageJobs.observe(getViewLifecycleOwner(),jobsResults -> {
            jobsAdapter.submitList(jobsResults.getResults());

            ProgressDialog.hideLoader();
        });


    }


    @Override
    public void onJobClick(JobsResults.Jobs job) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("jobDetails",job);

        Navigation.findNavController(fragmentHomeBinding.getRoot()).navigate(R.id.action_homeFragment_to_jobDetailFragment,bundle);
    }
}