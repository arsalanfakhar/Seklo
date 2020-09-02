package com.trulyfuture.seklo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.HrAdapter;
import com.trulyfuture.seklo.databinding.ActivityMainBinding;
import com.trulyfuture.seklo.databinding.HrServicesBottomSheetBinding;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BottomSheetBehavior bottomSheetBehavior;
    private HrAdapter hrAdapter;
    private HrServicesBottomSheetBinding hrServicesBottomSheetBinding;

    private MainActivityViewModel activityViewModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init(){

        activityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);


        activityViewModel.userResults.observe(this,userResults -> {
            Log.v(TAG,String.valueOf(userResults.getUserResultList().get(0).getFullName()));
        });


        //no tint to bottom nav icons
        binding.bottomNavigationView.setItemIconTintList(null);

        //setup bottom navigation
        NavController navController= Navigation.findNavController(this,R.id.navHostfragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);

        //Bottom sheet binding
        ConstraintLayout hrconstraintLayout=findViewById(R.id.hr_bottom_sheet);
        hrServicesBottomSheetBinding=HrServicesBottomSheetBinding.bind(hrconstraintLayout);

        //Setting bottom sheet default state
        bottomSheetBehavior=BottomSheetBehavior.from(hrconstraintLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        binding.hrServiceBtn.setOnClickListener(view -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        //Setup adapter
        hrAdapter=new HrAdapter(this);
        GridLayoutManager jobsGridLayoutManager=new GridLayoutManager(this,1);
        jobsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hrServicesBottomSheetBinding.servicesHrRV.setLayoutManager(jobsGridLayoutManager);
        hrServicesBottomSheetBinding.servicesHrRV.setAdapter(hrAdapter);

        hrServicesBottomSheetBinding.hrBookframeLayout.setOnClickListener(view -> {
            Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        });

    }


}