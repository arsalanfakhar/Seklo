package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;

public class TalentTeamAdapter extends RecyclerView.Adapter<TalentTeamAdapter.TalentTeamAdapterViewHolder> {

    private Context mContext;

    public TalentTeamAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public TalentTeamAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TalentTeamAdapterViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.talent_team_rv_item_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TalentTeamAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class TalentTeamAdapterViewHolder extends RecyclerView.ViewHolder{

        public TalentTeamAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
