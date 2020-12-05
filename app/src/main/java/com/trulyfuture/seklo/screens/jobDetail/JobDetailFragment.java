package com.trulyfuture.seklo.screens.jobDetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentJobDetailBinding;
import com.trulyfuture.seklo.models.JobApply;
import com.trulyfuture.seklo.models.JobsResults;


public class JobDetailFragment extends Fragment {
    private FragmentJobDetailBinding jobDetailBinding;

    private JobsResults.Jobs currentJob;
    private JobDetailViewModel viewModel;
    private MainActivityViewModel activityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        jobDetailBinding = FragmentJobDetailBinding.inflate(getLayoutInflater());
        return jobDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            currentJob = (JobsResults.Jobs) bundle.getSerializable("jobDetails");
            loadJobData();
        }

        setupViews();

    }

    private void setupViews() {
        viewModel = ViewModelProviders.of(this).get(JobDetailViewModel.class);
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        jobDetailBinding.applyJobBtn.setOnClickListener(v -> {
            viewModel.cvExists(activityViewModel.getUserId()).observe(getViewLifecycleOwner(), cvExistsResults -> {
                if (cvExistsResults.getResults().get(0).getReplyCode() == 1) {

                    //Intitialize job apply objects

                    JobApply.JobDBObj jobDBObj = new JobApply.JobDBObj();
                    jobDBObj.setUserId(activityViewModel.getUserId());
                    jobDBObj.setJobId(currentJob.getJobID());

                    JobApply.InfoObj infoObj = new JobApply.InfoObj();
                    infoObj.setUserId(activityViewModel.getUserId());
                    infoObj.setCompanyName(currentJob.getCompanyName());
                    infoObj.setJobName(currentJob.getJobTitle());
                    infoObj.setJobLocation(currentJob.getLocation());
                    infoObj.setJobEmail(currentJob.getCompanyEmail());

                    JobApply jobApply = new JobApply();
                    jobApply.setdBObj(jobDBObj);
                    jobApply.setInfoObj(infoObj);

                    //Apply for job
                    viewModel.applyForJob(jobApply).observe(getViewLifecycleOwner(), sekloResults -> {

                        if (sekloResults.getResults().getCode() == 1) {
                            Toast.makeText(getContext(), "Applied for job sucesfully", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        } else {
                            Toast.makeText(getContext(), sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

                } else {
                    Toast.makeText(getContext(), "Please add your CV before applying", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void loadJobData() {
        Glide.with(this).asBitmap().load(currentJob.getProfilePic()).into(jobDetailBinding.companyImage);


        jobDetailBinding.companyName.setText(currentJob.getCompanyName());
        jobDetailBinding.jobTitle.setText(currentJob.getJobTitle());


        jobDetailBinding.jobDescription.setText(Html.fromHtml(currentJob.getJobDescription()).toString());
        jobDetailBinding.jobRequirements.setText(Html.fromHtml(currentJob.getJobRequirements()).toString());

        jobDetailBinding.jobPostedOn.setText(currentJob.getPostDate().substring(0, 10));
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