package com.trulyfuture.seklo.screens.profile.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentProfileOverviewBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.screens.profile.ProfileViewModel;


public class ProfileOverviewFragment extends Fragment {

    private static final String TAG = "ProfileOverviewFragment";
    private FragmentProfileOverviewBinding binding;
    private MainActivityViewModel activityViewModel;
    private Users currentUser;
    private ProfileViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG,"OncreateView");
        // Inflate the layout for this fragment
        binding = FragmentProfileOverviewBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG,"OnViewCreate");
        setupViews();
        setupObserver();

    }

    private void setupViews() {
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel=ViewModelProviders.of(this).get(ProfileViewModel.class);

        binding.overviewAddUpdateBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_editProfileFragment);
        });
    }

    private void setupObserver() {
        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), userResults -> {
            if (userResults.getCode() == 1) {
                currentUser = userResults.getUserResultList().get(0);
                loadUserdata();
            } else {
                //TODO Handle error on user
            }

        });
    }

    private void loadUserdata() {

        if (!TextUtils.isEmpty(currentUser.getOverview())) {
            binding.overviewTxt.setText(currentUser.getOverview());
            binding.overviewAddUpdateBtn.setText("Update Overview");
        }
        else {
            binding.overviewTxt.setText("Not provided yet");
            binding.overviewAddUpdateBtn.setText("Add Overview");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG,"OnStart");
        setupObserver();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"OnResume");
        setupObserver();
    }



}