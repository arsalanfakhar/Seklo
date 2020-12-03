package com.trulyfuture.seklo.screens.profile.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.ExperienceAdapter;
import com.trulyfuture.seklo.databinding.AddExperiencePopupBinding;
import com.trulyfuture.seklo.databinding.FragmentProfileExperienceBinding;
import com.trulyfuture.seklo.models.EmploymentResults;
import com.trulyfuture.seklo.models.ExperienceResults;
import com.trulyfuture.seklo.screens.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileExperienceFragment extends Fragment implements ExperienceAdapter.onExperienceClickListener {
    private FragmentProfileExperienceBinding binding;
    private ExperienceAdapter experieceAdapter;
    private MainActivityViewModel activityViewModel;
    private ProfileViewModel viewModel;

    private AddExperiencePopupBinding popupBinding;
    private List<EmploymentResults.EmploymentType> employmentTypeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileExperienceBinding.inflate(getLayoutInflater());
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

        binding.addExperrienceBtn.setOnClickListener(view -> {
            showAddExperiencePopup();
        });

        experieceAdapter = new ExperienceAdapter(getContext(), new ArrayList<>(), this);
        binding.experienceRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.experienceRv.setAdapter(experieceAdapter);


    }

    private void setupObservers() {

        activityViewModel.employmentResults.observe(getViewLifecycleOwner(), employmentResults -> {
            employmentTypeList = employmentResults.getResults();
        });

        getUserExperience();
    }

    private void getUserExperience() {
        activityViewModel.getAllUserExperience(activityViewModel.getUserId()).observe(getViewLifecycleOwner(), experienceResults -> {
            if (experienceResults.getResults() != null)
                experieceAdapter.setExperienceArrayList((ArrayList<ExperienceResults.Experience>) experienceResults.getResults());
        });

    }

    private void showAddExperiencePopup() {
        popupBinding = AddExperiencePopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(popupBinding.getRoot());

        //Initialize popup data
        ArrayAdapter<EmploymentResults.EmploymentType> employmentTypeArrayAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_item_layout,
                employmentTypeList
        );

        //Getting last 60 years
        ArrayList<String> startYearList = new ArrayList<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int tillYear = currentYear - 60;

        //last 60 years from current date
        for (int i = currentYear; i >= tillYear; i--) {
            startYearList.add(String.valueOf(i));
        }

        ArrayAdapter<String> startYearArrayAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item_layout, startYearList
        );

        ArrayList<String> endYearList = new ArrayList<>(startYearList);
        endYearList.remove(0);
        endYearList.add(0, "Till present");

        ArrayAdapter<String> endYearArrayAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item_layout, endYearList
        );

        popupBinding.employmentType.setAdapter(employmentTypeArrayAdapter);
        popupBinding.startYear.setAdapter(startYearArrayAdapter);
        popupBinding.endYear.setAdapter(endYearArrayAdapter);

        //On start year click listener
        popupBinding.startYear.setOnItemClickListener((parent, view, position, id) -> {
            setEndYear(Integer.valueOf(popupBinding.startYear.getText().toString()));
        });

        popupBinding.startYearBtn.setOnClickListener(view -> {
            popupBinding.startYear.showDropDown();
        });

        popupBinding.endYearBtn.setOnClickListener(view -> {
            popupBinding.endYear.showDropDown();
        });

        popupBinding.employmentTypeDropBtn.setOnClickListener(view -> {
            popupBinding.employmentType.showDropDown();
        });


        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        popupBinding.closeBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        popupBinding.addExperienceBtn.setOnClickListener(view -> {
            if (!isFieldEmpty()) {
                Map<String, Object> experienceMap = new HashMap<>();
                experienceMap.put("userId", activityViewModel.getUserId());
                experienceMap.put("Title", popupBinding.jobTitle.getText().toString());
                experienceMap.put("company", popupBinding.companyName.getText().toString());
                experienceMap.put("empType", getEmploymentTypeId(popupBinding.employmentType.getText().toString()));
                experienceMap.put("startDate", popupBinding.startYear.getText().toString());
                experienceMap.put("endDate", popupBinding.endYear.getText().toString());

                viewModel.addUserExperience(experienceMap).observe(getViewLifecycleOwner(), sekloResults -> {
                    if (sekloResults.getResults().getCode() == 1) {
                        getUserExperience();
                        Toast.makeText(getContext(), sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }

        });

    }

    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(popupBinding.jobTitle.getText())
                || TextUtils.isEmpty(popupBinding.employmentType.getText())
                || TextUtils.isEmpty(popupBinding.companyName.getText())
                || TextUtils.isEmpty(popupBinding.startYear.getText())
                || TextUtils.isEmpty(popupBinding.endYear.getText())) {

            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private int getEmploymentTypeId(String employmentName) {
        for (EmploymentResults.EmploymentType employmentType : employmentTypeList) {
            if (employmentType.getDescription().equals(employmentName)) {
                return employmentType.getListID();
            }
        }
        return 0;
    }

    private void setEndYear(Integer startYear) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentYear=currentYear-1;
        ArrayList<String> endYearList = new ArrayList<>();

        for (int i = currentYear; i > startYear; i--) {
            endYearList.add(String.valueOf(i));
        }
        endYearList.add(0, "Till present");

        ArrayAdapter<String> endYearArrayAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item_layout, endYearList
        );

        popupBinding.endYear.setAdapter(endYearArrayAdapter);
    }

    @Override
    public void onExperienceClick(ExperienceResults.Experience experience) {

        viewModel.deleteExperience(experience.getExpId()).observe(getViewLifecycleOwner(),sekloResults -> {
            if(sekloResults.getResults().getCode()==1){
                getUserExperience();
            }
        });


    }
}