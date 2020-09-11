package com.trulyfuture.seklo.screens.companies;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivityViewModel;
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
    private MainActivityViewModel activityViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompanyBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);

        setupViews();
        setupObservers();
    }

    private void setupViews() {
        companiesAdapter = new CompaniesAdapter(getContext(), this, new ArrayList<>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.companiesRV.setLayoutManager(gridLayoutManager);
        binding.companiesRV.setAdapter(companiesAdapter);
    }

    private void setupObservers() {
        activityViewModel.allCompanies.observe(getViewLifecycleOwner(),companyResults -> {
            companiesAdapter.setCompanyArrayList((ArrayList<CompanyResults.Company>) companyResults.getResults());
        });
    }

    @Override
    public void onCompanyClick(CompanyResults.Company company) {
        Bundle args = new Bundle();
        args.putInt("companyId", company.getID());

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_companyFragment_to_companyDetailFragment, args);
    }

}