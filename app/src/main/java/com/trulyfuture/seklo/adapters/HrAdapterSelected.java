package com.trulyfuture.seklo.adapters;

import android.content.Context;
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

public class HrAdapterSelected extends RecyclerView.Adapter<HrAdapterSelected.HrAdapterViewHolder> {

    private Context mContext;

    private ArrayList<HrResults.Hr> hrArrayList;
    private onHrAdapterSelected onHrAdapterSelected;
    private ArrayList<Boolean> selectedHrList;


    public HrAdapterSelected(Context mContext, onHrAdapterSelected onHrAdapterSelected,ArrayList<Boolean> selectedHrList) {
        this.mContext = mContext;
        hrArrayList = new ArrayList<>();
        this.onHrAdapterSelected = onHrAdapterSelected;
        this.selectedHrList=selectedHrList;
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

        if(selectedHrList.get(position)){
            holder.selectedImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.selectedImage.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return hrArrayList.size();
    }

    public void setHrArrayList(ArrayList<HrResults.Hr> hrArrayList) {
        this.hrArrayList = hrArrayList;

        //Initialize selested List
        ArrayList<Boolean> booleanArrayList=new ArrayList<>();
        for(int i=0;i<hrArrayList.size();i++){
            booleanArrayList.add(false);
        }
        this.selectedHrList=booleanArrayList;

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
            onHrAdapterSelected.onHrSelected(hrArrayList.get(getAdapterPosition()));

            //If already selected then deselect it
            if(selectedHrList.get(getAdapterPosition())){
                selectedHrList.set(getAdapterPosition(),false);
                notifyDataSetChanged();
            }
            else{
                //select hr
                setSelectedHr(getAdapterPosition());
            }



        }
    }

    public interface onHrAdapterSelected {
        void onHrSelected(HrResults.Hr selectedHr);
    }

    private void setSelectedHr(int pos){
        for(int i=0;i<selectedHrList.size();i++){

            if(i==pos)
                selectedHrList.set(i,true);
            else
                selectedHrList.set(i,false);
        }
        notifyDataSetChanged();
    }



}
