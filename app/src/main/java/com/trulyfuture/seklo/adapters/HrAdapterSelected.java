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
import com.trulyfuture.seklo.models.HrResults;

import java.util.ArrayList;

public class HrAdapterSelected extends RecyclerView.Adapter<HrAdapterSelected.HrAdapterViewHolder> {

    private Context mContext;

    private ArrayList<HrResults.Hr> hrArrayList;
    private onHrAdapterSelected onHrAdapterSelected;

    public HrAdapterSelected(Context mContext, onHrAdapterSelected onHrAdapterSelected) {
        this.mContext = mContext;
        hrArrayList = new ArrayList<>();
        this.onHrAdapterSelected = onHrAdapterSelected;
    }

    @NonNull
    @Override
    public HrAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HrAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hr_rv_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HrAdapterViewHolder holder, int position) {
        HrResults.Hr currentHr = hrArrayList.get(position);
        holder.name.setText(currentHr.getFullName());

        Glide.with(mContext).asBitmap()
                .load(currentHr.getProfilePic())
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return hrArrayList.size();
    }

    public void setHrArrayList(ArrayList<HrResults.Hr> hrArrayList) {
        this.hrArrayList = hrArrayList;
        notifyDataSetChanged();
    }

    class HrAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image, selectedImage;
        TextView name;

        public HrAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.hrImage);
            name = itemView.findViewById(R.id.hrName);
            selectedImage = itemView.findViewById(R.id.hr_check);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            selectedImage.setVisibility(View.VISIBLE);
            onHrAdapterSelected.onHrSelected(hrArrayList.get(getAdapterPosition()));
        }
    }

    public interface onHrAdapterSelected {
        void onHrSelected(HrResults.Hr selectedHr);
    }

}
