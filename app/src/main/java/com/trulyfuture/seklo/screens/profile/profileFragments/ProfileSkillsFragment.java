package com.trulyfuture.seklo.screens.profile.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentProfileSkillsBinding;

public class ProfileSkillsFragment extends Fragment {

    private FragmentProfileSkillsBinding binding;
    private MainActivityViewModel activityViewModel;
    private static final String TAG = "ProfileSkillsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileSkillsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews();
        setupObservers();
    }

    private void setupViews() {
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        binding.skillDropBtn.setOnClickListener(view -> {
            binding.skillNameTxt.showDropDown();
        });


    }


    private void setupObservers() {
        activityViewModel.userSkills.observe(getViewLifecycleOwner(), skillResults -> {
            Log.v(TAG, skillResults.toString());
        });

        activityViewModel.skillResults.observe(getViewLifecycleOwner(), skillResults -> {
            Log.v(TAG, skillResults.toString());
        });

    }



}