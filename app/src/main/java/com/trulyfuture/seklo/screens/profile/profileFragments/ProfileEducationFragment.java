package com.trulyfuture.seklo.screens.profile.profileFragments;

import android.os.Bundle;

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
import com.trulyfuture.seklo.adapters.EducationAdapter;
import com.trulyfuture.seklo.databinding.AddEducationPopupBinding;
import com.trulyfuture.seklo.databinding.FragmentProfileEducationBinding;
import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.EducationResults;
import com.trulyfuture.seklo.models.StudyFieldsResults;
import com.trulyfuture.seklo.screens.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileEducationFragment extends Fragment implements EducationAdapter.OnEducationClickListener {

    private FragmentProfileEducationBinding binding;
    private EducationAdapter educationAdapter;
    private MainActivityViewModel activityViewModel;
    private ProfileViewModel viewModel;

    private static final String TAG = "ProfileEducationFragmen";

    private List<DegreeResults.Degrees> degreesList;
    private List<StudyFieldsResults.StudyFields> studyFieldsList;

    private AddEducationPopupBinding educationPopupBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileEducationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setupViews();
        setupObservers();


    }

    private void setupViews() {

        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);


        educationAdapter = new EducationAdapter(getContext(),new ArrayList<>(),this);
        binding.educationRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.educationRv.setAdapter(educationAdapter);


        binding.addEducationBtn.setOnClickListener(view -> {
            showAddEducationPopup();
        });

    }

    private void setupObservers() {
        activityViewModel.degreeResults.observe(getViewLifecycleOwner(), degreeResults -> {
            if (degreeResults.getResults() != null) {
                degreesList = degreeResults.getResults();

            }


        });

        activityViewModel.studyFieldsResults.observe(getViewLifecycleOwner(), studyFieldsResults -> {

            if (studyFieldsResults.getResults() != null) {
                studyFieldsList = studyFieldsResults.getResults();
            }


        });

        activityViewModel.educationResults.observe(getViewLifecycleOwner(),educationResults -> {
            if(educationResults.getResults()!=null){
                educationAdapter.setEducationModelArrayList((ArrayList<EducationResults.EducationModel>) educationResults.getResults());
            }
        });


    }


    private void showAddEducationPopup() {
        educationPopupBinding = AddEducationPopupBinding.inflate(getLayoutInflater());

//        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
//
//        View view = layoutInflaterAndroid.inflate(R.layout.add_education_popup, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(educationPopupBinding.getRoot());

        //Initialize popup data

        ArrayAdapter<DegreeResults.Degrees> degreesArrayAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item_layout, degreesList
        );


        ArrayAdapter<StudyFieldsResults.StudyFields> studyFieldsArrayAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item_layout, studyFieldsList
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


        educationPopupBinding.fieldOfStudy.setAdapter(studyFieldsArrayAdapter);
        educationPopupBinding.degreeName.setAdapter(degreesArrayAdapter);
        educationPopupBinding.startYear.setAdapter(startYearArrayAdapter);
        educationPopupBinding.endYear.setAdapter(endYearArrayAdapter);


        educationPopupBinding.degreeDropBtn.setOnClickListener(view -> {
            educationPopupBinding.degreeName.showDropDown();
        });

        educationPopupBinding.studyDropBtn.setOnClickListener(view -> {
            educationPopupBinding.fieldOfStudy.showDropDown();
        });

        educationPopupBinding.startYearBtn.setOnClickListener(view -> {
            educationPopupBinding.startYear.showDropDown();
        });

        educationPopupBinding.endYearBtn.setOnClickListener(view -> {
            educationPopupBinding.endYear.showDropDown();
        });


        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        educationPopupBinding.addEducationBtn.setOnClickListener(view -> {
            if(!isFieldEmpty()){

                Map<String,Object> educationMap=new HashMap<>();
                educationMap.put("userId",activityViewModel.getUserId());
                educationMap.put("uniName",educationPopupBinding.schoolName.getText().toString());
                educationMap.put("degreeId",getDegreeId(educationPopupBinding.degreeName.getText().toString()) );
                educationMap.put("studyId",getStudyFieldId(educationPopupBinding.fieldOfStudy.getText().toString()) );
                educationMap.put("startYear", educationPopupBinding.startYear.getText().toString());
                educationMap.put("endYear",educationPopupBinding.endYear.getText().toString());

                viewModel.addUserEducation(educationMap).observe(getViewLifecycleOwner(),sekloResults -> {
                    if(sekloResults.getResults().getCode()==1){
                        Toast.makeText(getContext(),sekloResults.getResults().getMessage(),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });


    }


    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(educationPopupBinding.schoolName.getText())
                || TextUtils.isEmpty(educationPopupBinding.degreeName.getText())
                || TextUtils.isEmpty(educationPopupBinding.fieldOfStudy.getText())
                || TextUtils.isEmpty(educationPopupBinding.startYear.getText())
                || TextUtils.isEmpty(educationPopupBinding.endYear.getText())) {

            Toast.makeText(getContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private int getDegreeId(String userDegree){
        for(DegreeResults.Degrees degree:degreesList){
            if(degree.getDegreeName().equals(userDegree)){
                return degree.getDegreeId();
            }
        }
        return 0;
    }

    private int getStudyFieldId(String userstudyField){
        for(StudyFieldsResults.StudyFields studyField:studyFieldsList){
            if(studyField.getStudyName().equals(userstudyField)){
                return studyField.getStudyId();
            }
        }
        return 0;
    }

    @Override
    public void onEducationClick(EducationResults.EducationModel educationModel) {

    }
}