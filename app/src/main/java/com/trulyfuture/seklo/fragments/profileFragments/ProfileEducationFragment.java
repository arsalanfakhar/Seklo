package com.trulyfuture.seklo.fragments.profileFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.ProfileAdapter;
import com.trulyfuture.seklo.databinding.FragmentProfileEducationBinding;


public class ProfileEducationFragment extends Fragment {

    private FragmentProfileEducationBinding binding;
    private ProfileAdapter educationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileEducationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        educationAdapter=new ProfileAdapter(getContext());
        binding.educationRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.educationRv.setAdapter(educationAdapter);
    }
}