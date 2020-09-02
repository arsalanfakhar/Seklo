package com.trulyfuture.seklo.screens.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.adapters.HrAdapter;
import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.databinding.FragmentHomeBinding;
import com.trulyfuture.seklo.models.HrResults;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding fragmentHomeBinding;
    private JobsAdapter jobsAdapter;
    private HrAdapter hrAdapter;

    private MainActivityViewModel activityViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding=FragmentHomeBinding.inflate(getLayoutInflater());
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);

        init();

        loadData();
    }

    private void loadData() {

        activityViewModel.hrResults.observe(getViewLifecycleOwner(),hrResults -> {
            if(hrResults.getHrList()!=null)
                hrAdapter.setHrArrayList((ArrayList<HrResults.Hr>) hrResults.getHrList());
        });

    }

    private void init(){
        jobsAdapter=new JobsAdapter(getContext());
        GridLayoutManager hrGridLayoutManager=new GridLayoutManager(getContext(),2);
        hrGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentHomeBinding.jobsRV.setLayoutManager(hrGridLayoutManager);
        fragmentHomeBinding.jobsRV.setAdapter(jobsAdapter);

        hrAdapter=new HrAdapter(getContext());
        fragmentHomeBinding.hrRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        fragmentHomeBinding.hrRV.setAdapter(hrAdapter);



    }
}