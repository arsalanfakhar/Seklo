package com.trulyfuture.seklo.screens.companies;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.CompaniesAdapter;
import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.database.retrofit.SeekloApiInterface;
import com.trulyfuture.seklo.databinding.FragmentCompanyBinding;
import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.models.HrResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyFragment extends Fragment implements CompaniesAdapter.OnCompanyClickListener {

    private FragmentCompanyBinding binding;

    private CompaniesAdapter companiesAdapter;
    private Application application;
    private SeekloApiInterface apiInterface;



    public void getAllCompanies() {
        //MutableLiveData<CompanyResults> data = new MutableLiveData<>();
        Call<CompanyResults> CompanyResultsCall = apiInterface.getAllCompanies();

        CompanyResultsCall.enqueue(new Callback<CompanyResults>() {
            @Override
            public void onResponse(Call<CompanyResults> call, Response<CompanyResults> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                        companiesAdapter.setCompanyArrayList((ArrayList<CompanyResults.Company>) response.body().getResults());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCompanyBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiInterface = RetrofitService.getInterface();
        companiesAdapter=new CompaniesAdapter(getContext(),this,new ArrayList<>());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.companiesRV.setLayoutManager(gridLayoutManager);
        binding.companiesRV.setAdapter(companiesAdapter);
         getAllCompanies();
    }

    public MutableLiveData<HrResults> getAllHr() {
        MutableLiveData<HrResults> data = new MutableLiveData<>();
        Call<HrResults> hrResultsCall = apiInterface.getAllHr();

        hrResultsCall.enqueue(new Callback<HrResults>() {
            @Override
            public void onResponse(Call<HrResults> call, Response<HrResults> response) {
                if (response.isSuccessful() && response.body() != null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HrResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    @Override
    public void onCompanyClick(CompanyResults.Company company) {
        Bundle args = new Bundle();
        args.putInt("companyId", company.getID());

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_companyFragment_to_companyDetailFragment,args);
    }

}