package com.trulyfuture.seklo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.repository.SeekloRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    private int userId;

    private MutableLiveData<UserResults> _userResults=new MutableLiveData<>();
    public LiveData<UserResults> userResults=_userResults;

    public LiveData<HrResults> hrResults=new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
        getAllHr();
    }

    public void setUserId(int userId) {
        this.userId = userId;
        getCurrentUser(userId);
    }

    public int getUserId(){
        return this.userId;
    }

    private void getCurrentUser(int id){
        _userResults=seekloRepository.getUserById(id);
    }

    private void getAllHr(){
        hrResults=seekloRepository.getAllHr();
    }



}
