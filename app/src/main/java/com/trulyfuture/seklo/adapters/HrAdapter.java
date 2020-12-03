package com.trulyfuture.seklo.adapters;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.models.HrResults;

import java.util.ArrayList;

public class HrAdapter extends RecyclerView.Adapter<HrAdapter.HrAdapterViewHolder> {

    private Context mContext;

    private ArrayList<HrResults.Hr> hrArrayList;

    private onHrClickListener mOnHrClickListener;

    public HrAdapter(Context mContext, onHrClickListener onHrClickListener) {
        this.mContext = mContext;
        hrArrayList = new ArrayList<>();
        this.mOnHrClickListener = onHrClickListener;
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

        //Progress Drawable
        CircularProgressDrawable progressDrawable=new CircularProgressDrawable(mContext);
        progressDrawable.setStrokeWidth(5f);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.setColorSchemeColors(Color.WHITE);
        progressDrawable.setBackgroundColor(Color.WHITE);
        progressDrawable.start();


        Glide.with(mContext).asBitmap()
                .load(currentHr.getProfilePic())
                .placeholder(progressDrawable)
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

        ImageView image;
        TextView name;

        public HrAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.hrImage);
            name = itemView.findViewById(R.id.hrName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnHrClickListener.onHrClick(hrArrayList.get(getAdapterPosition()));
        }
    }

    public interface onHrClickListener {
        void onHrClick(HrResults.Hr hr);
    }


}
