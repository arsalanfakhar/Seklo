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

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationAdapterViewHolder> {

    private Context mContext;

    public NotificationsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_rv_item_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class NotificationAdapterViewHolder extends RecyclerView.ViewHolder{

        public NotificationAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {

        private Context mContext;
        private ArrayList<EducationResults.EducationModel> educationModelArrayList;
        private OnEducationClickListener mOnEducationClickListener;

        public EducationAdapter(Context mContext, ArrayList<EducationResults.EducationModel> educationModelArrayList, OnEducationClickListener mOnEducationClickListener) {
            this.mContext = mContext;
            this.educationModelArrayList = educationModelArrayList;
            this.mOnEducationClickListener = mOnEducationClickListener;
        }

        @NonNull
        @Override
        public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EducationViewHolder(
                    ProfileItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
            holder.bind(educationModelArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return educationModelArrayList.size();
        }

        public void setEducationModelArrayList(ArrayList<EducationResults.EducationModel> educationModelArrayList) {
            this.educationModelArrayList = educationModelArrayList;
            notifyDataSetChanged();
        }

        public class EducationViewHolder extends RecyclerView.ViewHolder {

            private ProfileItemBinding profileItemBinding;

            public EducationViewHolder(@NonNull ProfileItemBinding profileItemBinding) {
                super(profileItemBinding.getRoot());
                this.profileItemBinding = profileItemBinding;
            }

            public void bind(EducationResults.EducationModel educationModel) {
                profileItemBinding.title.setText(educationModel.getUniName());

                String subtitle= educationModel.getStudyName()+" , "+educationModel.getDegreeName();
                profileItemBinding.subtitle.setText(subtitle);

                String duration=educationModel.getStartYear()+" - "+educationModel.getEndYear();
                profileItemBinding.duration.setText(duration);
            }

        }

        public interface OnEducationClickListener {
            void onEducationClick(EducationResults.EducationModel educationModel);
        }


    }
}
