package com.trulyfuture.seklo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.databinding.FragmentNotificationDetailBinding;
import com.trulyfuture.seklo.models.NotificationModel;


public class NotificationDetailFragment extends Fragment {

    private FragmentNotificationDetailBinding binding;
    private NotificationModel.NotificationResult mNotificationResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNotificationDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle notificationBundle=getArguments();
        if(notificationBundle!=null){
            mNotificationResult= (NotificationModel.NotificationResult) notificationBundle.getSerializable("notification");
            loadNotification();
        }

    }

    private void loadNotification(){
        Glide.with(this).load(mNotificationResult.getImage()).into(binding.notificationImage);
        binding.notificationTitle.setText(mNotificationResult.getTitle());
        binding.notificationDescription.setText(mNotificationResult.getDescription());
    }

}