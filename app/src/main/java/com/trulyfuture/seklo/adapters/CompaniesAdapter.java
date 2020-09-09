package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.models.CompanyResults;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompanyAdapterViewHolder> {

    private Context mContext;
    private OnCompanyClickListener onCompanyClickListener;
    private ArrayList<CompanyResults.Company> companyArrayList;

    public CompaniesAdapter(Context mContext, OnCompanyClickListener onCompanyClickListener, ArrayList<CompanyResults.Company> companyArrayList) {
        this.mContext = mContext;
        this.onCompanyClickListener = onCompanyClickListener;
        this.companyArrayList = companyArrayList;
    }



    public void setCompanyArrayList(ArrayList<CompanyResults.Company> companyArrayList) {
        this.companyArrayList = companyArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompanyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyAdapterViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.companies_rv_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapterViewHolder holder, int position) {

        Glide.with(mContext).load(companyArrayList.get(position).getProfilePic()).into(holder.img);
    }




    @Override
    public int getItemCount() {
        return companyArrayList.size();

    }

    class CompanyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img;

        public CompanyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.hrImage);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCompanyClickListener.onCompanyClick(companyArrayList.get(getAdapterPosition()));
        }
    }

    public interface OnCompanyClickListener {
        void onCompanyClick(CompanyResults.Company company);
    }
}
