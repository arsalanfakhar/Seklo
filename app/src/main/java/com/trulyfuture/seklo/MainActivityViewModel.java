package com.trulyfuture.seklo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.repository.SeekloRepository;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

public class MainActivityViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    private int userId;


    public LiveData<UserResults> userResults = new MutableLiveData<>();

    public LiveData<HrResults> hrResults = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        seekloRepository = new SeekloRepository(application);
//        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(application.getApplicationContext(), SharedPreferenceClass.UserDetails);
//        userId=sharedPreferenceClass.getInteger("userId");
        userId=9;
        initialize();
    }

    private void initialize(){
        getCurrentUser(userId);
        getAllHr();
    }

    public int getUserId() {
        return this.userId;
    }

    private void getCurrentUser(int id) {
        userResults = seekloRepository.getUserById(id);
//
//        return seekloRepository.getUserById(id);
    }

    private void getAllHr() {
        hrResults = seekloRepository.getAllHr();
    }

}
