package com.trulyfuture.seklo.screens.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.ProfileViewPagerAdapter;
import com.trulyfuture.seklo.databinding.FragmentProfileBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.screens.login.LoginActivity;
import com.trulyfuture.seklo.utils.ProgressDialog;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;


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

        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        ProgressDialog.showLoader(getActivity());

        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), userResults -> {
            if (userResults.getCode() == 1) {
                currentUser = userResults.getUserResultList().get(0);
                loadUserdata();
                ProgressDialog.hideLoader();
            } else {
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

//            Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_uploadCVFragment);
        });

        binding.overviewBtn.setOnClickListener(view -> {
            binding.profileViewpager.setCurrentItem(0);
        });

        binding.educationBtn.setOnClickListener(view -> {
            binding.profileViewpager.setCurrentItem(1);
        });

        binding.experienceBtn.setOnClickListener(view -> {
            binding.profileViewpager.setCurrentItem(2);
        });

        binding.skillsBtn.setOnClickListener(view -> {
            binding.profileViewpager.setCurrentItem(3);
        });

    }

    private void changeViewPagerTabsSelection(int selected) {
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
            case 2: {
                binding.overviewBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.educationBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.experienceBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                binding.skillsBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
            }
            break;
            case 3: {
                binding.overviewBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.educationBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.experienceBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
                binding.skillsBtnLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            }
            break;
        }
    }

    private void loadUserdata() {
        if (TextUtils.isEmpty(currentUser.getUserImage())) {
            //TODO Load dummy image
            Glide.with(this)
                    .load(R.drawable.nouser)
                    .into(binding.userImage);
        } else {
            Glide.with(this).asBitmap()
                    .load(currentUser.getUserImage())
                    .into(binding.userImage);
            // Decode base64 string to image
//            String removeAdditionalText=currentUser.getUserImage().substring(22);
//
//            byte[] decodedString = Base64.decode(removeAdditionalText, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            binding.userImage.setImageBitmap(decodedByte);
//
//            Glide.with(this).asBitmap()
//                    .load(currentUser.getUserImage())
//                    .into(binding.userImage);
        }


        binding.userName.setText(currentUser.getFullName());
//        binding.userOverview.setText(currentUser.getOverview());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding.profileViewpager.unregisterOnPageChangeCallback(viewpagerCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"onResume");
        binding.profileViewpager.setAdapter(new ProfileViewPagerAdapter(getActivity()));
    }


}