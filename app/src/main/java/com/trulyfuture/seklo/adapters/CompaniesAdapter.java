package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompanyAdapterViewHolder> {

    private Context mContext;

    public CompaniesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CompanyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyAdapterViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.companies_rv_item_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 16;
    }

    class CompanyAdapterViewHolder extends RecyclerView.ViewHolder{

        public CompanyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
