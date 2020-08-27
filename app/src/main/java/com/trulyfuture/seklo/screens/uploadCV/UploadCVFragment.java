package com.trulyfuture.seklo.screens.uploadCV;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentUploadCVBinding;


public class UploadCVFragment extends Fragment {

    private FragmentUploadCVBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentUploadCVBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


}