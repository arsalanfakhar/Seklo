package com.trulyfuture.seklo.screens.jobDetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentJobDetailBinding;
import com.trulyfuture.seklo.models.JobsResults;


public class JobDetailFragment extends Fragment {
    private FragmentJobDetailBinding jobDetailBinding;

    private JobsResults.Jobs currentJob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        jobDetailBinding=FragmentJobDetailBinding.inflate(getLayoutInflater());
        return jobDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle=getArguments();

        if(bundle!=null){
            currentJob= (JobsResults.Jobs) bundle.getSerializable("jobDetails");
            loadJobData();
        }


    }

    private void loadJobData(){
        Glide.with(this).asBitmap().load(currentJob.getProfilePic()).into(jobDetailBinding.companyImage);


        jobDetailBinding.companyName.setText(currentJob.getCompanyName());
        jobDetailBinding.jobTitle.setText(currentJob.getJobTitle());
        jobDetailBinding.jobDescription.setText(currentJob.getJobDescription());
        jobDetailBinding.jobRequirements.setText(currentJob.getJobRequirements());
        jobDetailBinding.jobPostedOn.setText(currentJob.getPostDate().substring(0,10));
        jobDetailBinding.jobExpiryOn.setText(currentJob.getExpiresDate());
        jobDetailBinding.jobMinimumPay.setText(currentJob.getMinPay());
        jobDetailBinding.jobMaximumPay.setText(currentJob.getMaxPay());
        jobDetailBinding.jobCompanyName.setText(currentJob.getCompanyName());
        jobDetailBinding.jobCompanyEmail.setText(currentJob.getCompanyEmail());
        jobDetailBinding.jobLocation.setText(currentJob.getLocation());
        jobDetailBinding.jobType.setText(currentJob.getJobType());
        jobDetailBinding.jobCategory.setText(currentJob.getCategory());



    }

}