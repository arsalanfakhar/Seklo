package com.trulyfuture.seklo.screens.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.ProfileViewPagerAdapter;
import com.trulyfuture.seklo.databinding.FragmentProfileBinding;
import com.trulyfuture.seklo.models.Users;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewPagerAdapter viewPagerAdapter;

    private ViewPager2.OnPageChangeCallback viewpagerCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            changeViewPagerTabsSelection(position);
        }
    };

    private MainActivityViewModel activityViewModel;
    private Users currentUser;

    private static final String TAG = "ProfileFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

        activityViewModel.userResults.observe(getViewLifecycleOwner(),userResults -> {
            if(userResults.getCode()==1){
                currentUser=userResults.getUserResultList().get(0);
                loadUserdata();
            }
            else {
                //TODO Handle error on user
            }

        });

        viewPagerAdapter = new ProfileViewPagerAdapter(getActivity());
        binding.profileViewpager.setAdapter(viewPagerAdapter);

        binding.profileViewpager.registerOnPageChangeCallback(viewpagerCallback);

        binding.editProfileBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_editProfileFragment);
        });

        binding.uploadCvBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_uploadCVFragment);
        });

    }

    private void changeViewPagerTabsSelection(int selected) {
        Toast.makeText(getContext(), selected + "", Toast.LENGTH_SHORT).show();
        switch (selected) {
            case 0: {
                binding.overviewBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.educationBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.experienceBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.skillsBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
            }

            break;
            case 1: {
                binding.overviewBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.educationBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.experienceBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.skillsBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
            }
            break;
            case 2:{
                binding.overviewBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.educationBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.experienceBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.skillsBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
            }
            break;
            case 3:{
                binding.overviewBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.educationBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.experienceBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.skillsBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            }
            break;
        }
    }

    private void loadUserdata(){
        binding.userName.setText(currentUser.getFullName());
        binding.userOverview.setText(currentUser.getOverview());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.profileViewpager.unregisterOnPageChangeCallback(viewpagerCallback);
    }



}