package com.trulyfuture.seklo.screens.companyDetail;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.adapters.CompaniesAdapter;
import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.adapters.TalentTeamAdapter;
import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.database.retrofit.SeekloApiInterface;
import com.trulyfuture.seklo.databinding.FragmentCompanyDetailBinding;
import com.trulyfuture.seklo.models.CompanyResults;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompanyDetailFragment extends Fragment {
    private FragmentCompanyDetailBinding binding;
    private JobsAdapter jobsAdapter;
    private TalentTeamAdapter talentTeamAdapter;
    private CompaniesAdapter companiesAdapter;
    private Application application;
    private SeekloApiInterface apiInterface;
    private CompanyResults.Company company;
    private Integer companyId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompanyDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    public void getCompanyById(int id) {
        //MutableLiveData<CompanyResults> data = new MutableLiveData<>();
        Call<CompanyResults> CompanyByIdCall = apiInterface.getCompanyById(id);

        CompanyByIdCall.enqueue(new Callback<CompanyResults>() {
            @Override
            public void onResponse(Call<CompanyResults> call, Response<CompanyResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    company = response.body().getResults().get(0);
                    loadData();
                    //   companiesAdapter.setCompanyArrayList((ArrayList<CompanyResults.Company>) response.body().getResults().get(0));
                }
                //   data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CompanyResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiInterface = RetrofitService.getInterface();


        Bundle companyBundle =getArguments();
        if (companyBundle!=null)
             companyId=companyBundle.getInt("companyId");

        jobsAdapter = new JobsAdapter(getContext());
        GridLayoutManager jobsGridLayoutManager = new GridLayoutManager(getContext(), 1);
        jobsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.jobsRV.setLayoutManager(jobsGridLayoutManager);
        binding.jobsRV.setAdapter(jobsAdapter);

        talentTeamAdapter = new TalentTeamAdapter(getContext());
        GridLayoutManager talentTeamGridLayoutManager = new GridLayoutManager(getContext(), 1);
        talentTeamGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.talentTeamRV.setLayoutManager(talentTeamGridLayoutManager);
        binding.talentTeamRV.setAdapter(talentTeamAdapter);
        getCompanyById(companyId);
    }

    private void loadData() {

        binding.companyName.setText(company.getName());
        binding.companyOverview.setText(company.getOverview());
        binding.companyFoundingDate.setText(company.getFounded());
        binding.companyEmployeesNo.setText(company.getNumOfEmlpyees());

    }
}