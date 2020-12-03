package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.firestore.v1.PreconditionOrBuilder;
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

        if(companyArrayList.get(position).getProfilePic()==null || companyArrayList.get(position).getProfilePic().isEmpty()){
            holder.img.setVisibility(View.INVISIBLE);
            holder.dummyImage.setVisibility(View.VISIBLE);



        }
        else {
            holder.img.setVisibility(View.VISIBLE);

            holder.dummyImage.setVisibility(View.INVISIBLE);

            //Progress Drawable
            CircularProgressDrawable progressDrawable=new CircularProgressDrawable(mContext);
            progressDrawable.setStrokeWidth(5f);
            progressDrawable.setCenterRadius(30f);
            progressDrawable.setColorSchemeColors(Color.WHITE);
            progressDrawable.setBackgroundColor(Color.WHITE);
            progressDrawable.start();

            Glide.with(mContext)
                    .load(companyArrayList.get(position).getProfilePic())
                    .placeholder(progressDrawable)
                    .into(holder.img);
        }

    }




    @Override
    public int getItemCount() {
        return companyArrayList.size();

    }

    class CompanyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img;
        ImageView dummyImage;

        public CompanyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.hrImage);
            dummyImage=itemView.findViewById(R.id.hrDummyImage);
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
