package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.JobsRvItemLayoutBinding;
import com.trulyfuture.seklo.models.JobsResults;

import java.util.ArrayList;

public class JobsAdapter extends ListAdapter<JobsResults.Jobs, JobsAdapter.JobsViewHolder>{

    private OnJobClickListerner onJobClickListerner;

    public JobsAdapter(OnJobClickListerner onJobClickListerner) {
        super(JOBS_DIFF_CALLBACK);
        this.onJobClickListerner=onJobClickListerner;
    }

    @NonNull
    @Override
    public JobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobsViewHolder(
                JobsRvItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull JobsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    class JobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        JobsRvItemLayoutBinding binding;

        public JobsViewHolder(@NonNull JobsRvItemLayoutBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        public void bind(JobsResults.Jobs job){
            binding.jobName.setText(job.getJobTitle());

            Glide.with(binding.getRoot()).asBitmap().load(job.getProfilePic()).into(binding.jobImage);
        }

        @Override
        public void onClick(View view) {
            onJobClickListerner.onJobClick(getItem(getAdapterPosition()));
        }
    }

    public interface OnJobClickListerner {
        void onJobClick(JobsResults.Jobs job);
    }

    private static final DiffUtil.ItemCallback<JobsResults.Jobs> JOBS_DIFF_CALLBACK=new DiffUtil.ItemCallback<JobsResults.Jobs>() {
        @Override
        public boolean areItemsTheSame(@NonNull JobsResults.Jobs oldItem, @NonNull JobsResults.Jobs newItem) {
            return oldItem.getJobID().equals(newItem.getJobID());
        }

        @Override
        public boolean areContentsTheSame(@NonNull JobsResults.Jobs oldItem, @NonNull JobsResults.Jobs newItem) {
            return oldItem.getJobTitle().equals(newItem.getJobTitle());
        }
    };

}
