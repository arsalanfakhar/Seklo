package com.trulyfuture.seklo.screens.notification;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.adapters.NotificationsAdapter;
import com.trulyfuture.seklo.databinding.FragmentNotificationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;
    private NotificationsAdapter notificationsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNotificationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        notificationsAdapter=new NotificationsAdapter(getContext());
        binding.notificationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notificationRV.setAdapter(notificationsAdapter);
    }
}