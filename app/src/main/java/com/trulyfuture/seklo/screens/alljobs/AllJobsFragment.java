package com.trulyfuture.seklo.screens.alljobs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.AllJobsAdapter;
import com.trulyfuture.seklo.databinding.FragmentAllJobsBinding;
import com.trulyfuture.seklo.models.JobsResults;

import java.util.ArrayList;


public class AllJobsFragment extends Fragment implements AllJobsAdapter.onJobClickListener {

    private FragmentAllJobsBinding binding;

    private AllJobsAdapter allJobsAdapter;
    private MainActivityViewModel activityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAllJobsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        setupViews();
        setupObservers();
    }

    private void setupViews(){
        allJobsAdapter=new AllJobsAdapter(getContext(),new ArrayList<>(),this);
        binding.alljobsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.alljobsRv.setAdapter(allJobsAdapter);

    }

    private void setupObservers(){
        activityViewModel.allJobs.observe(getViewLifecycleOwner(),jobsResults -> {
            allJobsAdapter.setJobsArrayList((ArrayList<JobsResults.Jobs>) jobsResults.getResults());
        });
    }


    @Override
    public void onJobClick(JobsResults.Jobs job) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("jobDetails",job);

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_allJobsFragment_to_jobDetailFragment,bundle);
    }

}