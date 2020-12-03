package com.trulyfuture.seklo.screens.editProfile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.repository.SeekloRepository;

import java.util.Map;

public class EditProfileViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);

    }

    public LiveData<SekloResults> updateUserDetails(Users users, int userId){
        return seekloRepository.updateUserDetails(users,userId);
    }

    public LiveData<SekloResults> updateUserImage(Map<String,String> userMap,int userId){
        return seekloRepository.updateUserImage(userMap,userId);
    }

    public LiveData<SekloResults> updateUserOverView(Users users, int userId) {
        return seekloRepository.updateUserDetails(users, userId);
    }


}
