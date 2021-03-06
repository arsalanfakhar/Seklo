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
import com.trulyfuture.seklo.models.EmploymentResults;
import com.trulyfuture.seklo.models.ExperienceResults;
import com.trulyfuture.seklo.screens.profile.profileFragments.ProfileEducationFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    private Context mContext;
    private ArrayList<ExperienceResults.Experience> experienceArrayList;
    private onExperienceClickListener onExperienceClickListener;
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public ExperienceAdapter(Context mContext, ArrayList<ExperienceResults.Experience> experienceArrayList, ExperienceAdapter.onExperienceClickListener onExperienceClickListener) {
        this.mContext = mContext;
        this.experienceArrayList = experienceArrayList;
        this.onExperienceClickListener = onExperienceClickListener;
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
        holder.bind(experienceArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return experienceArrayList.size();
    }

    public void setExperienceArrayList(ArrayList<ExperienceResults.Experience> experienceArrayList) {
        this.experienceArrayList = experienceArrayList;
        notifyDataSetChanged();
    }

    public class ExperienceViewHolder extends RecyclerView.ViewHolder {

        private ProfileItemBinding profileItemBinding;

        public ExperienceViewHolder(@NonNull ProfileItemBinding profileItemBinding) {
            super(profileItemBinding.getRoot());
            this.profileItemBinding = profileItemBinding;
        }

        public void bind(ExperienceResults.Experience experience) {

            profileItemBinding.getRoot().setOnClickListener(view -> {
                onExperienceClickListener.onExperienceClick(experience);
            });

            profileItemBinding.title.setText(experience.getTitle());
            String subtitle=experience.getCompany()+" - "+experience.getEmpDes();
            profileItemBinding.subtitle.setText(subtitle);


            String duration = "";

            if (experience.getEndDate()==currentYear) {
                duration=experience.getStartDate()+" - "+ProfileEducationFragment.till_present_string;
            } else
                duration=experience.getStartDate()+" - "+experience.getEndDate();

            profileItemBinding.duration.setText(duration);

            profileItemBinding.edit.setOnClickListener(view -> {
                onExperienceClickListener.onExperienceClick(experienceArrayList.get(getAdapterPosition()));
            });


        }

    }

    public interface onExperienceClickListener {
        void onExperienceClick(ExperienceResults.Experience experience);
    }

}
