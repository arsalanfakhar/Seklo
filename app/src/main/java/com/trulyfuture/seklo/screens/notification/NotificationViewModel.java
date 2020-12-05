package com.trulyfuture.seklo.screens.notification;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trulyfuture.seklo.models.NotificationModel;
import com.trulyfuture.seklo.repository.SeekloRepository;

public class NotificationViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<NotificationModel> getAllNotifications(int userId){
       return seekloRepository.getAllNotifications(userId);
    }

    public LiveData<NotificationModel> getNotificationDetail(int notificationId){
        return seekloRepository.getAllNotifications(notificationId);
    }

}
