package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.TalentTeamRvItemLayoutBinding;
import com.trulyfuture.seklo.models.CompanyHrResults;

import java.util.ArrayList;

public class TalentTeamAdapter extends RecyclerView.Adapter<TalentTeamAdapter.TalentTeamAdapterViewHolder> {

    private Context mContext;
    private ArrayList<CompanyHrResults.CompanyHr> hrResultsArrayList;


    public TalentTeamAdapter(Context mContext) {
        this.mContext = mContext;
        this.hrResultsArrayList = new ArrayList<>();
    }


    @NonNull
    @Override
    public TalentTeamAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TalentTeamAdapterViewHolder(
                TalentTeamRvItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TalentTeamAdapterViewHolder holder, int position) {
        holder.bind(hrResultsArrayList.get(position));
    }

    public void setHrResultsArrayList(ArrayList<CompanyHrResults.CompanyHr> hrResultsArrayList) {
        this.hrResultsArrayList = hrResultsArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hrResultsArrayList.size();
    }

    class TalentTeamAdapterViewHolder extends RecyclerView.ViewHolder {

        private TalentTeamRvItemLayoutBinding binding;

        public TalentTeamAdapterViewHolder(@NonNull TalentTeamRvItemLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(CompanyHrResults.CompanyHr companyHr) {

            binding.teamNameTitle.setText(companyHr.getHrName());

            binding.teamDescription.setText(companyHr.getHrDescrip());

            Glide.with(binding.getRoot()).load(companyHr.getHrImage()).into(binding.teamImage);
        }
    }
}
