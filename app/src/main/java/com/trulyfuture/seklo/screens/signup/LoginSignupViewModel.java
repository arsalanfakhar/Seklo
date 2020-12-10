package com.trulyfuture.seklo.screens.signup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.repository.SeekloRepository;

import java.util.Map;

public class LoginSignupViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public LoginSignupViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<Results> createUser(Users user){
        return seekloRepository.createUser(user);
    }

    public LiveData<Results> authenticateUser(Users user){
        return seekloRepository.loginUser(user);
    }

    public LiveData<SekloResults> resetPassword(Map<String,Object> passMap){
        return seekloRepository.resetPassword(passMap);
    }

    public LiveData<SekloResults> facebookLogin(Map<String,Object> fbUserMap){
        return seekloRepository.facebookLoginUser(fbUserMap);
    }
}
