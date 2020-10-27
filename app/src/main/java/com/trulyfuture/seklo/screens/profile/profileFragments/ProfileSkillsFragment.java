package com.trulyfuture.seklo.screens.profile.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentProfileSkillsBinding;
import com.trulyfuture.seklo.models.SkillResults;
import com.trulyfuture.seklo.screens.profile.ProfileViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileSkillsFragment extends Fragment {

    private FragmentProfileSkillsBinding binding;
    private MainActivityViewModel activityViewModel;
    private static final String TAG = "ProfileSkillsFragment";
    private List<SkillResults.Skills> userSkills;
    private List<SkillResults.Skills> allSkills;
    private ProfileViewModel viewModel;
    private int currentSkillId;

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
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        binding.skillDropBtn.setOnClickListener(view -> {
            binding.skillNameTxt.showDropDown();
        });

        binding.addSkillBtn.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(binding.skillNameTxt.getText())) {
                if (!checkUserSkills(binding.skillNameTxt.getText().toString())) {

                    Map<String, Object> skillMap = new HashMap<>();
                    skillMap.put("userId", activityViewModel.getUserId());
                    skillMap.put("skillId", getSkillId(binding.skillNameTxt.getText().toString()));

                    //Add a skill
                    viewModel.addUserSkill(skillMap).observe(getViewLifecycleOwner(),sekloResults -> {
                        if(sekloResults.getResults().getCode()==1){
                            getUserSkills();

                            Toast.makeText(getContext(),sekloResults.getResults().getMessage(),Toast.LENGTH_SHORT).show();


                            binding.skillNameTxt.setText("");
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Skill already exists.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please select a skill", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setupObservers() {
        getUserSkills();

        //Get list of all skills
        activityViewModel.skillResults.observe(getViewLifecycleOwner(), skillResults -> {
            allSkills=skillResults.getResults();
            ArrayAdapter<SkillResults.Skills> skillResultsArrayAdapter = new ArrayAdapter<>(
                    getContext(),
                    R.layout.spinner_item_layout,
                    skillResults.getResults()
            );

            binding.skillNameTxt.setAdapter(skillResultsArrayAdapter);

        });


    }

    private boolean checkUserSkills(String skillName) {
        for (SkillResults.Skills skill : userSkills) {
            if (skill.getSkillName().equals(skillName)) {
                return true;
            }
        }
        return false;
    }

    private int getSkillId(String skillName){
        for (SkillResults.Skills skill : allSkills) {
            if (skill.getSkillName().equals(skillName)) {
                return skill.getSkillId();
            }
        }
        return 0;
    }

    private int getUserSkillId(String skillName){
        for (SkillResults.Skills skill : userSkills) {
            if (skill.getSkillName().equals(skillName)) {
                return skill.getUserSkillId();
            }
        }
        return 0;
    }


    private void getUserSkills(){
        //Add user skills
        activityViewModel.getUserSkills(activityViewModel.getUserId()).observe(getViewLifecycleOwner(), skillResults -> {
            userSkills = skillResults.getResults();
            addUserSkillChips();
        });

    }

    private void addUserSkillChips() {
        //To clear data
        binding.skillChipGroup.removeAllViewsInLayout();

        for (SkillResults.Skills skill : userSkills) {
            Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.skill_chips, null, false);
            chip.setText(skill.getSkillName());

            //Delete user skills
            chip.setOnCloseIconClickListener(view -> {
                viewModel.deleteUserSkill(getUserSkillId(chip.getText().toString())).observe(this,sekloResults -> {
                    if(sekloResults.getResults().getCode()==1){
                        getUserSkills();
                    }
                });
            });

            binding.skillChipGroup.addView(chip);
        }
    }
}