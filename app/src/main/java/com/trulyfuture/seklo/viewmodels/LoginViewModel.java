package com.trulyfuture.seklo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.models.UsersShort;
import com.trulyfuture.seklo.repository.SeekloRepository;

public class LoginViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<Results> createUser(UsersShort user){
        return seekloRepository.createUser(user);
    }

}
