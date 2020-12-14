package com.trulyfuture.seklo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.HrAdapter;
import com.trulyfuture.seklo.adapters.HrAdapterSelected;
import com.trulyfuture.seklo.databinding.ActivityMainBinding;
import com.trulyfuture.seklo.databinding.CareerCounselingPopupBinding;
import com.trulyfuture.seklo.databinding.CoverLetterPopupBinding;
import com.trulyfuture.seklo.databinding.HrServicesBottomSheetBinding;
import com.trulyfuture.seklo.databinding.InstructionsPopupBinding;
import com.trulyfuture.seklo.databinding.ResumeReviewPopupBinding;
import com.trulyfuture.seklo.databinding.ResumeWritingPopupBinding;
import com.trulyfuture.seklo.models.HRServices;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.ResumeResults;
import com.trulyfuture.seklo.models.ServicesResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.payment.PaymentActivity;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

import org.w3c.dom.Text;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HrAdapterSelected.onHrAdapterSelected {
    private ActivityMainBinding binding;
    private BottomSheetBehavior bottomSheetBehavior;
    private HrAdapterSelected hrAdapter;
    private HrServicesBottomSheetBinding hrServicesBottomSheetBinding;

    private MainActivityViewModel activityViewModel;
    private Users currentUser;
    private ResumeResults.Resume userResume;
    private List<ServicesResults.Services> servicesList;

    private int userId;
    private static final String TAG = "MainActivity";

    private HrResults.Hr selectedHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {

        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(this, SharedPreferenceClass.UserDetails);
        userId = sharedPreferenceClass.getInteger("userId");


        setupViews();
        setupObservers();


    }

    private void setupViews() {

        try {
            FirebaseMessaging.getInstance().subscribeToTopic("general");
            String TOKEN = FirebaseInstanceId.getInstance().getToken();


        } catch (Exception e) {

        }


        //no tint to bottom nav icons
        binding.bottomNavigationView.setItemIconTintList(null);

        //setup bottom navigation
        NavController navController = Navigation.findNavController(this, R.id.navHostfragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        //Bottom sheet binding
        NestedScrollView hrconstraintLayout = findViewById(R.id.hr_bottom_sheet);
        hrServicesBottomSheetBinding = HrServicesBottomSheetBinding.bind(hrconstraintLayout);

        //Setting bottom sheet default state
        bottomSheetBehavior = BottomSheetBehavior.from(hrconstraintLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //to disable the drag

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    binding.bottomNavigationView.setVisibility(View.VISIBLE);
                    binding.hrServiceBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        binding.hrServiceBtn.setOnClickListener(view -> {
            SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(this, SharedPreferenceClass.UserDetails);

            boolean firstTime = sharedPreferenceClass.getBoolean("firstTime");

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            binding.bottomNavigationView.setVisibility(View.GONE);
            binding.hrServiceBtn.setVisibility(View.GONE);

            if (firstTime) {
                openInstructionPopup();
                addToSharedPrefs();
            }
        });

        //Setup adaptergi
        ArrayList<Boolean> selectedList = new ArrayList<>();


        hrAdapter = new HrAdapterSelected(this, this, new ArrayList<>());
        GridLayoutManager jobsGridLayoutManager = new GridLayoutManager(this, 1);
        jobsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hrServicesBottomSheetBinding.servicesHrRV.setLayoutManager(jobsGridLayoutManager);
        hrServicesBottomSheetBinding.servicesHrRV.setAdapter(hrAdapter);


        hrServicesBottomSheetBinding.closeServicesBtn.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
            binding.hrServiceBtn.setVisibility(View.VISIBLE);
        });

        hrServicesBottomSheetBinding.resumeReviewframeLayout.setOnClickListener(v -> {

            getUser();
            if (selectedHr == null) {
                Toast.makeText(this, "Select a HR before proceeding", Toast.LENGTH_SHORT).show();
            } else if (userResume == null) {
                Toast.makeText(this, "Submit your resume before proceeding", Toast.LENGTH_SHORT).show();
            } else {
                showResumeReviewPopup();
            }

        });

        hrServicesBottomSheetBinding.careerCounselingFrameLayout.setOnClickListener(v -> {
            getUser();
            if (selectedHr == null) {
                Toast.makeText(this, "Select a HR before proceeding", Toast.LENGTH_SHORT).show();
            } else {
                showCareerCounselingPopup();
            }


        });

        hrServicesBottomSheetBinding.resumeWritingFrameLayout.setOnClickListener(v -> {
            getUser();
            if (selectedHr == null) {
                Toast.makeText(this, "Select a HR before proceeding", Toast.LENGTH_SHORT).show();
            } else {
                showResumeWritingPopup();
            }


        });

        hrServicesBottomSheetBinding.coverLetterFrameLayout.setOnClickListener(v -> {
            getUser();
            if (selectedHr == null) {
                Toast.makeText(this, "Select a HR before proceeding", Toast.LENGTH_SHORT).show();
            } else {
                showCoverLetterPopup();
            }


        });

    }

    private void setupObservers() {

        getUser();
        activityViewModel.hrResults.observe(this, hrResults -> {
            if (hrResults.getHrList() != null)
                hrAdapter.setHrArrayList((ArrayList<HrResults.Hr>) hrResults.getHrList());
        });


        activityViewModel.allSevices.observe(this, servicesResults -> {
            servicesList = servicesResults.getResults();
        });

    }

    private void getUser() {
        activityViewModel.getCurrentUser().observe(this, userResults -> {
            currentUser = userResults.getUserResultList().get(0);
        });

        activityViewModel.getUserResume(activityViewModel.getUserId()).observe(this, resumeResults -> {
            if (!resumeResults.getResults().isEmpty())
                userResume = resumeResults.getResults().get(0);
        });

    }

    private void showResumeReviewPopup() {
        ResumeReviewPopupBinding resumeReviewPopupBinding = ResumeReviewPopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(resumeReviewPopupBinding.getRoot());

        int serviceId = 1;
        List<ServicesResults.Services> sublist = getServicesListById(serviceId);

        List<String> daysList = new ArrayList<>();

        for (ServicesResults.Services service : sublist) {
            daysList.add("" + service.getDays());
        }
//        daysList.add("Days: 2");
//        daysList.add("Days: 4");
//        daysList.add("Days: 6");


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, daysList);
        resumeReviewPopupBinding.timeSlot.setAdapter(stringArrayAdapter);

        //To change cost on click
        resumeReviewPopupBinding.timeSlot.setOnItemClickListener((adapterView, view, i, l) -> {

            String day = resumeReviewPopupBinding.timeSlot.getText().toString();

            for (ServicesResults.Services service : sublist) {
                if (service.getDays().toString().equals(day)) {
                    resumeReviewPopupBinding.costTxt.setText("Rs " + service.getTotalInPKR().intValue());
                    break;
                }
            }
        });


        resumeReviewPopupBinding.timeSlotDropdown.setOnClickListener(v -> {
            resumeReviewPopupBinding.timeSlot.showDropDown();
        });
        resumeReviewPopupBinding.email.setText(currentUser.getEmail());

        if (userResume != null)
            resumeReviewPopupBinding.resumeName.setText(userResume.getResumeName());


        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        resumeReviewPopupBinding.closePopup.setOnClickListener(v -> {
            dialog.dismiss();
        });


        resumeReviewPopupBinding.proceedBtn.setOnClickListener(v -> {


            if (TextUtils.isEmpty(resumeReviewPopupBinding.costTxt.getText())
                    || TextUtils.isEmpty(resumeReviewPopupBinding.timeSlot.getText())
                    || TextUtils.isEmpty(resumeReviewPopupBinding.resumeName.getText())) {

                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            } else {

                //Open Payment Page

                HRServices hrServices = new HRServices();

                HRServices.DBObj dbObj = new HRServices.DBObj();
                dbObj.sethRID(selectedHr.getId());
                dbObj.setUserID(userId);

                String costStr = resumeReviewPopupBinding.costTxt.getText().toString().substring(3);

                dbObj.setPayment(Integer.valueOf(costStr));
                dbObj.setCurrency("PKR");
                dbObj.setDays(Integer.valueOf(resumeReviewPopupBinding.timeSlot.getText().toString()));

                HRServices.EmailObj emailObj = new HRServices.EmailObj();
                emailObj.setUserEmail(currentUser.getEmail());
                emailObj.setUserName(currentUser.getFullName());
                emailObj.sethREmail(selectedHr.getEmail());

                hrServices.setdBObj(dbObj);
                hrServices.setEmailObj(emailObj);

                Intent intent = new Intent(this, WebviewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("serviceName", WebviewActivity.ServicesName.ResumeReview.stringValue);
                mBundle.putSerializable("servicesData", hrServices);
                intent.putExtras(mBundle);

                startActivity(intent);

//                activityViewModel.addResumeReview(hrServices).observe(this, sekloResults -> {
//                    if (sekloResults.getResults().getCode() == 1) {
//                        Intent intent=new Intent(this, WebviewActivity.class);
//                        Bundle mBundle=new Bundle();
//
//                        mBundle.putSerializable("servicesData",hrServices);
//                        intent.putExtras(mBundle);
//
//                        startActivity(intent);
//
//                    }
//                });

            }


        });


    }

    private void showCareerCounselingPopup() {
        CareerCounselingPopupBinding popupBinding = CareerCounselingPopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(popupBinding.getRoot());

        int serviceId = 2;
        List<ServicesResults.Services> sublist = getServicesListById(serviceId);

        List<String> daysList = new ArrayList<>();

        for (ServicesResults.Services service : sublist) {
            daysList.add("" + service.getDays());
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, daysList);
        popupBinding.timeSlot.setAdapter(stringArrayAdapter);

        //To change cost on click
        popupBinding.timeSlot.setOnItemClickListener((adapterView, view, i, l) -> {

            String day = popupBinding.timeSlot.getText().toString();

            for (ServicesResults.Services service : sublist) {
                if (service.getDays().toString().equals(day)) {
                    popupBinding.costTxt.setText("Rs " + service.getTotalInPKR().intValue());
                    break;
                }
            }

        });


        popupBinding.timeSlopDropdown.setOnClickListener(v -> {
            popupBinding.timeSlot.showDropDown();
        });

        popupBinding.email.setText(currentUser.getEmail());
        popupBinding.name.setText(currentUser.getFullName());

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        popupBinding.closePopup.setOnClickListener(v -> {
            dialog.dismiss();
        });


        popupBinding.proceedBtn.setOnClickListener(v -> {


            if (TextUtils.isEmpty(popupBinding.costTxt.getText())
                    || TextUtils.isEmpty(popupBinding.timeSlot.getText())) {

                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            } else {

                HRServices hrServices = new HRServices();

                HRServices.DBObj dbObj = new HRServices.DBObj();
                dbObj.sethRID(selectedHr.getId());
                dbObj.setUserID(userId);
                String costStr = popupBinding.costTxt.getText().toString().substring(3);

                dbObj.setPayment(Integer.valueOf(costStr));

                dbObj.setCurrency("PKR");
                dbObj.setDays(Integer.valueOf(popupBinding.timeSlot.getText().toString()));
                dbObj.setTimeHours("45 minuts");

                HRServices.EmailObj emailObj = new HRServices.EmailObj();
                emailObj.setUserEmail(currentUser.getEmail());
                emailObj.setUserName(currentUser.getFullName());
                emailObj.sethREmail(selectedHr.getEmail());

                hrServices.setdBObj(dbObj);
                hrServices.setEmailObj(emailObj);

                Intent intent = new Intent(this, WebviewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("serviceName", WebviewActivity.ServicesName.CareerCounseling.stringValue);
                mBundle.putSerializable("servicesData", hrServices);
                intent.putExtras(mBundle);

                startActivity(intent);


//                activityViewModel.addCareerCounselling(hrServices).observe(this, sekloResults -> {
//                    if (sekloResults.getResults().getCode() == 1) {
//                        startActivity(new Intent(this, PaymentActivity.class));
//
//                    }
//                });

            }

        });
    }

    private void showResumeWritingPopup() {
        ResumeWritingPopupBinding popupBinding = ResumeWritingPopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(popupBinding.getRoot());

        int serviceId = 3;
        List<ServicesResults.Services> sublist = getServicesListById(serviceId);

        List<String> daysList = new ArrayList<>();

        for (ServicesResults.Services service : sublist) {
            daysList.add("" + service.getDays());
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, daysList);
        popupBinding.timeSlot.setAdapter(stringArrayAdapter);

        //To change cost on click
        popupBinding.timeSlot.setOnItemClickListener((adapterView, view, i, l) -> {

            String day = popupBinding.timeSlot.getText().toString();

            for (ServicesResults.Services service : sublist) {
                if (service.getDays().toString().equals(day)) {
                    popupBinding.costTxt.setText("Rs " + service.getTotalInPKR().intValue());
                    break;
                }
            }

        });

        popupBinding.timeSlopDropdown.setOnClickListener(v -> {
            popupBinding.timeSlot.showDropDown();
        });


        popupBinding.email.setText(currentUser.getEmail());

        List<String> choiceList = new ArrayList<>();
        choiceList.add("CV");
        choiceList.add("Resume");
        ArrayAdapter<String> choiceArrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, choiceList);
        popupBinding.choiceTxt.setAdapter(choiceArrayAdapter);

        popupBinding.choiceDropdown.setOnClickListener(view -> {
            popupBinding.choiceTxt.showDropDown();
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        popupBinding.closePopup.setOnClickListener(v -> {
            dialog.dismiss();
        });

        popupBinding.proceedBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(popupBinding.costTxt.getText())
                    || TextUtils.isEmpty(popupBinding.timeSlot.getText())
                    || TextUtils.isEmpty(popupBinding.choiceTxt.getText())) {

                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            } else {

                HRServices hrServices = new HRServices();

                HRServices.DBObj dbObj = new HRServices.DBObj();
                dbObj.sethRID(selectedHr.getId());
                dbObj.setUserID(userId);
                String costStr = popupBinding.costTxt.getText().toString().substring(3);

                dbObj.setPayment(Integer.valueOf(costStr));

                dbObj.setCurrency("PKR");
                dbObj.setDays(Integer.valueOf(popupBinding.timeSlot.getText().toString()));
                dbObj.setResumeType(popupBinding.choiceTxt.getText().toString());


                HRServices.EmailObj emailObj = new HRServices.EmailObj();
                emailObj.setUserEmail(currentUser.getEmail());
                emailObj.setUserName(currentUser.getFullName());
                emailObj.sethREmail(selectedHr.getEmail());

                hrServices.setdBObj(dbObj);
                hrServices.setEmailObj(emailObj);

                Intent intent = new Intent(this, WebviewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("serviceName", WebviewActivity.ServicesName.ResumeWriting.stringValue);
                mBundle.putSerializable("servicesData", hrServices);
                intent.putExtras(mBundle);

                startActivity(intent);

//                activityViewModel.addResumeWriting(hrServices).observe(this, sekloResults -> {
//                    if (sekloResults.getResults().getCode() == 1) {
//                        startActivity(new Intent(this, PaymentActivity.class));
//                    }
//                });

            }

        });
    }

    private void showCoverLetterPopup() {
        CoverLetterPopupBinding popupBinding = CoverLetterPopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(popupBinding.getRoot());

        int serviceId = 4;
        List<ServicesResults.Services> sublist = getServicesListById(serviceId);

        List<String> daysList = new ArrayList<>();

        for (ServicesResults.Services service : sublist) {
            daysList.add("" + service.getDays());
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, daysList);
        popupBinding.timeSlot.setAdapter(stringArrayAdapter);

        //To change cost on click
        popupBinding.timeSlot.setOnItemClickListener((adapterView, view, i, l) -> {

            String day = popupBinding.timeSlot.getText().toString();

            for (ServicesResults.Services service : sublist) {
                if (service.getDays().toString().equals(day)) {
                    popupBinding.costTxt.setText("Rs " + service.getTotalInPKR().intValue());
                    break;
                }
            }

        });

        popupBinding.timeSlotDropdown.setOnClickListener(v -> {
            popupBinding.timeSlot.showDropDown();
        });


        popupBinding.email.setText(currentUser.getEmail());

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        popupBinding.closePopup.setOnClickListener(v -> {
            dialog.dismiss();
        });

        popupBinding.proceedBtn.setOnClickListener(v -> {


            if (TextUtils.isEmpty(popupBinding.costTxt.getText())
                    || TextUtils.isEmpty(popupBinding.timeSlot.getText())
                    || TextUtils.isEmpty(popupBinding.companyName.getText())
                    || TextUtils.isEmpty(popupBinding.jobTitle.getText())) {

                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            } else {

                HRServices hrServices = new HRServices();

                HRServices.DBObj dbObj = new HRServices.DBObj();
                dbObj.sethRID(selectedHr.getId());
                dbObj.setUserID(userId);
                String costStr = popupBinding.costTxt.getText().toString().substring(3);

                dbObj.setPayment(Integer.valueOf(costStr));

                dbObj.setCurrency("PKR");
                dbObj.setDays(Integer.valueOf(popupBinding.timeSlot.getText().toString()));
                dbObj.setCompanyName(popupBinding.companyName.getText().toString());
                dbObj.setJobName(popupBinding.jobTitle.getText().toString());


                HRServices.EmailObj emailObj = new HRServices.EmailObj();
                emailObj.setUserEmail(currentUser.getEmail());
                emailObj.setUserName(currentUser.getFullName());
                emailObj.sethREmail(selectedHr.getEmail());

                hrServices.setdBObj(dbObj);
                hrServices.setEmailObj(emailObj);

                Intent intent = new Intent(this, WebviewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("serviceName", WebviewActivity.ServicesName.CoverLetter.stringValue);
                mBundle.putSerializable("servicesData", hrServices);
                intent.putExtras(mBundle);

                startActivity(intent);

//                activityViewModel.addCoverLetter(hrServices).observe(this, sekloResults -> {
//                    if (sekloResults.getResults().getCode() == 1) {
//                        startActivity(new Intent(this, PaymentActivity.class));
//
//                    }
//                });

            }

        });
    }


    private List<ServicesResults.Services> getServicesListById(int id) {
        List<ServicesResults.Services> sublist = new ArrayList<>();
        for (ServicesResults.Services service : servicesList) {
            if (service.getServicesID().equals(id)) {
                sublist.add(service);
            }
        }
        return sublist;
    }


    @Override
    public void onHrSelected(HrResults.Hr selectedHr) {
        //if same then deselect it
        if (this.selectedHr == selectedHr) {
            this.selectedHr = null;
            hrServicesBottomSheetBinding.selectServicesBtn.setVisibility(View.GONE);
        } else {
            this.selectedHr = selectedHr;
            hrServicesBottomSheetBinding.selectServicesBtn.setVisibility(View.VISIBLE);
        }

    }

    public void openInstructionPopup() {
        InstructionsPopupBinding popupBinding = InstructionsPopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(popupBinding.getRoot());

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        dialog.setCancelable(false);

        popupBinding.continueBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }

    private void addToSharedPrefs() {
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(this, SharedPreferenceClass.UserDetails);
        sharedPreferenceClass.SetBooleanEditor("firstTime", false);
        sharedPreferenceClass.DoCommit();
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ||
                bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HALF_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
            binding.hrServiceBtn.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }
}