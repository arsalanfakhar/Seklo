package com.trulyfuture.seklo.screens.companyDetail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.adapters.CompaniesAdapter;
import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.adapters.TalentTeamAdapter;
import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.databinding.FragmentCompanyDetailBinding;
import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.JobsResults;

import java.util.ArrayList;


public class CompanyDetailFragment extends Fragment implements JobsAdapter.OnJobClickListerner {
    private FragmentCompanyDetailBinding binding;
    private JobsAdapter jobsAdapter;
    private TalentTeamAdapter talentTeamAdapter;
    private CompanyResults.Company mCompany;
    private int companyId;

    private CompanyDetailViewModel viewModel;
    private MainActivityViewModel activityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompanyDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        Bundle companyBundle =getArguments();
        if (companyBundle!=null) {
            companyId = companyBundle.getInt("companyId");
        }

        setupViews();
        setupObservers();

    }

    private void setupViews(){

        viewModel= new ViewModelProvider(this).get(CompanyDetailViewModel.class);

        jobsAdapter = new JobsAdapter(this);
        GridLayoutManager jobsGridLayoutManager = new GridLayoutManager(getContext(), 1);
        jobsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.jobsRV.setLayoutManager(jobsGridLayoutManager);
        binding.jobsRV.setAdapter(jobsAdapter);

        talentTeamAdapter = new TalentTeamAdapter(getContext());
        GridLayoutManager talentTeamGridLayoutManager = new GridLayoutManager(getContext(), 1);
        talentTeamGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.talentTeamRV.setLayoutManager(talentTeamGridLayoutManager);
        binding.talentTeamRV.setAdapter(talentTeamAdapter);
    }

    private void setupObservers(){
        viewModel.getCompanyById(companyId).observe(getViewLifecycleOwner(),companyResults -> {
            mCompany =companyResults.getResults().get(0);
            loadCompanyData();
        });

        activityViewModel.allJobs.observe(getViewLifecycleOwner(),jobsResults -> {
            jobsAdapter.submitList(jobsResults.getResults());
        });
    }



    private void loadCompanyData() {

        binding.companyName.setText(mCompany.getName());
        binding.companyOverview.setText(mCompany.getOverview());
        binding.companyFoundingDate.setText(mCompany.getFounded());
        binding.companyEmployeesNo.setText(mCompany.getNumOfEmlpyees());

        Glide.with(this).load(mCompany.getProfilePic()).into(binding.companyProfilePic);
    }

    @Override
    public void onJobClick(JobsResults.Jobs job) {

    }
}