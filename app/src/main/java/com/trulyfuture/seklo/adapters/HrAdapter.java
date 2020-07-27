package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;

public class HrAdapter extends RecyclerView.Adapter<HrAdapter.HrAdapterViewHolder> {

    private Context mContext;

    public HrAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HrAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HrAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hr_rv_item_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HrAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class HrAdapterViewHolder extends RecyclerView.ViewHolder{

        public HrAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
