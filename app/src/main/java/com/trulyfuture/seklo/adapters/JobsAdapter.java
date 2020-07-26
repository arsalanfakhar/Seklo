package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobsViewHolder> {

    private Context mContext;

    public JobsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public JobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_rv_item_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull JobsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class JobsViewHolder extends RecyclerView.ViewHolder{

        public JobsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
