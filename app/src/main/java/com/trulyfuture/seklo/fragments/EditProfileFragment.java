package com.trulyfuture.seklo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentEditProfileBinding;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentEditProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

}