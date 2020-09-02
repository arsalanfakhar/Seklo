package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.ProfileItemBinding;
import com.trulyfuture.seklo.models.EducationResults;

import java.util.ArrayList;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    private Context mContext;


    public ExperienceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExperienceViewHolder(
                ProfileItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ExperienceViewHolder extends RecyclerView.ViewHolder{

        private ProfileItemBinding profileItemBinding;

        public ExperienceViewHolder(@NonNull ProfileItemBinding profileItemBinding) {
            super(profileItemBinding.getRoot());
            this.profileItemBinding = profileItemBinding;
        }

        public void bind(){

        }

    }


}
