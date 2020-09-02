package com.trulyfuture.seklo.screens.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.repository.SeekloRepository;

import java.util.Map;

public class ProfileViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<SekloResults> updateUserOverView(Users users, int userId){
        return seekloRepository.updateUserDetails(users,userId);
    }


    public LiveData<SekloResults> addUserEducation(Map<String,Object> educationMap){
        return seekloRepository.addUserEducation(educationMap);
    }




}
