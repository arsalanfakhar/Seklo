package com.trulyfuture.seklo.screens.companyDetail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.CompaniesAdapter;
import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.adapters.TalentTeamAdapter;
import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.databinding.FragmentCompanyDetailBinding;
import com.trulyfuture.seklo.models.CompanyHrResults;
import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.JobsResults;
import com.trulyfuture.seklo.utils.ProgressDialog;

import java.util.ArrayList;


public class CompanyDetailFragment extends Fragment implements JobsAdapter.OnJobClickListerner {
    private FragmentCompanyDetailBinding binding;
    private JobsAdapter jobsAdapter;
    private TalentTeamAdapter talentTeamAdapter;
    private CompanyResults.Company mCompany;

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

        ProgressDialog.showLoader(getActivity());

        activityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        Bundle companyBundle = getArguments();
        if (companyBundle != null) {
            mCompany = (CompanyResults.Company) companyBundle.getSerializable("company");
            loadCompanyData();
        }

        setupViews();
        setupObservers();

    }

    private void setupViews() {

        viewModel = new ViewModelProvider(this).get(CompanyDetailViewModel.class);

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

    private void setupObservers() {

        //Get company Jobs
        viewModel.getCompanyJobs(mCompany.getID()).observe(getViewLifecycleOwner(), jobsResults -> {
            jobsAdapter.submitList(jobsResults.getResults());
            if (jobsResults.getResults().isEmpty()) {
                binding.jobEmptyText.setVisibility(View.VISIBLE);
                binding.jobsRV.setVisibility(View.INVISIBLE);
            } else {
                binding.jobEmptyText.setVisibility(View.INVISIBLE);
                binding.jobsRV.setVisibility(View.VISIBLE);
            }
        });

        //Get company HR
        viewModel.getCompanyHr(mCompany.getID()).observe(getViewLifecycleOwner(), companyHrResults -> {
            talentTeamAdapter.setHrResultsArrayList((ArrayList<CompanyHrResults.CompanyHr>) companyHrResults.getHrList());
            if (ProgressDialog.isShowing())
                ProgressDialog.hideLoader();

            if (companyHrResults.getHrList().isEmpty()) {
                binding.talentTeamEmptyText.setVisibility(View.VISIBLE);
                binding.talentTeamRV.setVisibility(View.INVISIBLE);
            } else {
                binding.talentTeamEmptyText.setVisibility(View.INVISIBLE);
                binding.talentTeamRV.setVisibility(View.VISIBLE);
            }
        });

    }


    private void loadCompanyData() {

        binding.companyName.setText(mCompany.getName());
        binding.companyOverview.setText(mCompany.getOverview());
        binding.companyFoundingDate.setText(mCompany.getFounded());
        binding.companyEmployeesNo.setText(mCompany.getNumOfEmlpyees());

        if(mCompany.getProfilePic()!=null){
            Glide.with(this).load(mCompany.getProfilePic()).into(binding.companyProfilePic);
        }



    }

    @Override
    public void onJobClick(JobsResults.Jobs job) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("jobDetails",job);

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_companyDetailFragment_to_jobDetailFragment,bundle);
    }
}