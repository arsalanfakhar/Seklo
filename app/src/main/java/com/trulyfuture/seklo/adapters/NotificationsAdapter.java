package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.models.NotificationModel;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationAdapterViewHolder> {

    private Context mContext;
    private List<NotificationModel.NotificationResult> noticationModelList;
    private onNotificationClickListener mNotificationClickListener;

    public NotificationsAdapter(Context mContext, List<NotificationModel.NotificationResult> noticationModel, onNotificationClickListener mNotificationClickListener) {
        this.mContext = mContext;
        this.noticationModelList = noticationModel;
        this.mNotificationClickListener = mNotificationClickListener;
    }

    @NonNull
    @Override
    public NotificationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_rv_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapterViewHolder holder, int position) {
        Glide.with(mContext).load(noticationModelList.get(position).getImage()).into(holder.notificationImage);

        holder.notificationMainText.setText(noticationModelList.get(position).getTitle());
        holder.notificationSubtext.setText(noticationModelList.get(position).getDescription());

    }

    public void setNoticationModelList(List<NotificationModel.NotificationResult> noticationModelList) {
        this.noticationModelList = noticationModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noticationModelList.size();
    }

    class NotificationAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView notificationImage;
        private TextView notificationMainText, notificationSubtext;

        public NotificationAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationImage = itemView.findViewById(R.id.notificationImage);
            notificationMainText = itemView.findViewById(R.id.notificationTitle);
            notificationSubtext = itemView.findViewById(R.id.notification_description);

            itemView.setOnClickListener(view -> {
                mNotificationClickListener.onNotificationClick(noticationModelList.get(getAdapterPosition()));
            });

        }
    }

    public interface onNotificationClickListener{
        void onNotificationClick(NotificationModel.NotificationResult result);
    }


}
