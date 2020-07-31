package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private Context mContext;

    public ProfileAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ProfileViewHolder extends RecyclerView.ViewHolder{

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
