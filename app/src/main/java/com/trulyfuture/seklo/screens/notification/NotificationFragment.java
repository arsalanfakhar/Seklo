package com.trulyfuture.seklo.screens.notification;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.adapters.NotificationsAdapter;
import com.trulyfuture.seklo.databinding.FragmentNotificationBinding;
import com.trulyfuture.seklo.models.NotificationModel;
import com.trulyfuture.seklo.utils.ProgressDialog;

import java.util.ArrayList;


public class NotificationFragment extends Fragment implements NotificationsAdapter.onNotificationClickListener {
    private FragmentNotificationBinding binding;

    private NotificationsAdapter notificationsAdapter;
    private NotificationViewModel viewModel;
    private MainActivityViewModel activityViewModel;

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

        ProgressDialog.showLoader(getActivity());

        setupViews();
        setupObservers();

    }

    private void setupViews(){

        viewModel= ViewModelProviders.of(this).get(NotificationViewModel.class);
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        notificationsAdapter=new NotificationsAdapter(getContext(),new ArrayList<>(),this);
        binding.notificationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notificationRV.setAdapter(notificationsAdapter);
    }

    private void setupObservers(){
        viewModel.getAllNotifications(activityViewModel.getUserId()).observe(getViewLifecycleOwner(),notificationModel -> {
            if(ProgressDialog.isShowing())
                ProgressDialog.hideLoader();

            if(notificationModel.getResults().isEmpty()){
                binding.notificationHint.setVisibility(View.VISIBLE);
            }
            else {
                binding.notificationHint.setVisibility(View.GONE);

                notificationsAdapter.setNoticationModelList(notificationModel.getResults());

            }
        });

    }

    @Override
    public void onNotificationClick(NotificationModel.NotificationResult result) {
        Bundle args = new Bundle();
        args.putSerializable("notification",result);

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_notificationFragment_to_notificationDetailFragment,args);
    }
}