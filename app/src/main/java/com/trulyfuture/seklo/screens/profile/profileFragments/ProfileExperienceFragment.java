package com.trulyfuture.seklo.screens.profile.profileFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.adapters.EducationAdapter;
import com.trulyfuture.seklo.databinding.FragmentProfileExperienceBinding;


public class ProfileExperienceFragment extends Fragment {
    private FragmentProfileExperienceBinding binding;
    private EducationAdapter experieceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileExperienceBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//
    }
}