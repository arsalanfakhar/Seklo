package com.trulyfuture.seklo.screens.editProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentEditProfileBinding;
import com.trulyfuture.seklo.models.Users;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;

    private MainActivityViewModel activityViewModel;
    private Users currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        activityViewModel.userResults.observe(getViewLifecycleOwner(), userResults -> {

            if (userResults.getCode() == 1) {
                currentUser = userResults.getUserResultList().get(0);
                loadUserData();
            }
        });


        binding.saveBtn.setOnClickListener(view1 -> {
            if(!isFieldEmpty()){

            }

        });

    }

    private void loadUserData() {
        binding.firstName.setText(currentUser.getFname());
        binding.lastName.setText(currentUser.getLname());
        binding.email.setText(currentUser.getEmail());
        binding.mobileNumber.setText(currentUser.getNumber());
    }

    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(binding.firstName.getText())
                || TextUtils.isEmpty(binding.lastName.getText())
                || TextUtils.isEmpty(binding.email.getText())
                || TextUtils.isEmpty(binding.mobileNumber.getText())) {
            Toast.makeText(getContext(),"Please fill all fields to continue",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}