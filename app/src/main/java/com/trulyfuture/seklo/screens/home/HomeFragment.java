package com.trulyfuture.seklo.screens.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.adapters.HrAdapter;
import com.trulyfuture.seklo.adapters.JobsAdapter;
import com.trulyfuture.seklo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding fragmentHomeBinding;
    private JobsAdapter jobsAdapter;
    private HrAdapter hrAdapter;
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
        init();
    }

    private void init(){
        jobsAdapter=new JobsAdapter(getContext());
        GridLayoutManager hrGridLayoutManager=new GridLayoutManager(getContext(),2);
        hrGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentHomeBinding.jobsRV.setLayoutManager(hrGridLayoutManager);
        fragmentHomeBinding.jobsRV.setAdapter(jobsAdapter);

        hrAdapter=new HrAdapter(getContext());
        GridLayoutManager jobsGridLayoutManager=new GridLayoutManager(getContext(),1);
        jobsGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentHomeBinding.hrRV.setLayoutManager(jobsGridLayoutManager);
        fragmentHomeBinding.hrRV.setAdapter(hrAdapter);



    }
}