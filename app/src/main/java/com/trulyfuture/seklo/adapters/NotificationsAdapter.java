package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trulyfuture.seklo.R;

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
}
