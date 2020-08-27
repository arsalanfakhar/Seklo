package com.trulyfuture.seklo.screens.companies;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.CompaniesAdapter;
import com.trulyfuture.seklo.databinding.FragmentCompanyBinding;

public class CompanyFragment extends Fragment implements CompaniesAdapter.OnCompanyClickListener {

    private FragmentCompanyBinding binding;
    private CompaniesAdapter companiesAdapter;
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

        companiesAdapter=new CompaniesAdapter(getContext(),this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.companiesRV.setLayoutManager(gridLayoutManager);
        binding.companiesRV.setAdapter(companiesAdapter);

    }

    @Override
    public void onCompanyClick() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_companyFragment_to_companyDetailFragment);
    }
}