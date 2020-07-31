package com.trulyfuture.seklo.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.adapters.TalentTeamAdapter;
import com.trulyfuture.seklo.databinding.FragmentCompanyDetailBinding;


public class CompanyDetailFragment extends Fragment {
    private FragmentCompanyDetailBinding binding;
    private JobsAdapter jobsAdapter;
    private TalentTeamAdapter talentTeamAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCompanyDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        jobsAdapter=new JobsAdapter(getContext());
        GridLayoutManager jobsGridLayoutManager=new GridLayoutManager(getContext(),1);
        jobsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.jobsRV.setLayoutManager(jobsGridLayoutManager);
        binding.jobsRV.setAdapter(jobsAdapter);

        talentTeamAdapter=new TalentTeamAdapter(getContext());
        GridLayoutManager talentTeamGridLayoutManager=new GridLayoutManager(getContext(),1);
        talentTeamGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.talentTeamRV.setLayoutManager(talentTeamGridLayoutManager);
        binding.talentTeamRV.setAdapter(talentTeamAdapter);
    }
}