package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.databinding.AlljobsItemLayoutBinding;
import com.trulyfuture.seklo.databinding.JobsRvItemLayoutBinding;
import com.trulyfuture.seklo.models.JobsResults;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AllJobsAdapter extends RecyclerView.Adapter<AllJobsAdapter.AllJobsViewHolder> {

    private Context mContext;
    private ArrayList<JobsResults.Jobs> jobsArrayList;
    private onJobClickListener onJobClickListener;

    public AllJobsAdapter(Context mContext, ArrayList<JobsResults.Jobs> jobsArrayList, AllJobsAdapter.onJobClickListener onJobClickListener) {
        this.mContext = mContext;
        this.jobsArrayList = jobsArrayList;
        this.onJobClickListener = onJobClickListener;
    }

    @NonNull
    @Override
    public AllJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllJobsViewHolder(
                AlljobsItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AllJobsViewHolder holder, int position) {
        holder.bind(jobsArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return jobsArrayList.size();
    }

    public void setJobsArrayList(ArrayList<JobsResults.Jobs> jobsArrayList) {
        this.jobsArrayList = jobsArrayList;
        notifyDataSetChanged();
    }

    class AllJobsViewHolder extends RecyclerView.ViewHolder {

        AlljobsItemLayoutBinding binding;

        public AllJobsViewHolder(@NonNull AlljobsItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                onJobClickListener.onJobClick(jobsArrayList.get(getAdapterPosition()));
            });

        }

        public void bind(JobsResults.Jobs job) {
            Glide.with(binding.getRoot()).asBitmap().load(job.getProfilePic()).into(binding.allJobImage);

            binding.allJobTitle.setText(job.getJobTitle());
            binding.allJobCompany.setText(job.getCompanyName());
            binding.allJobPostingDate.setText(job.getPostDate().substring(0,10));

        }
    }

    public interface onJobClickListener {
        void onJobClick(JobsResults.Jobs job);
    }


}
