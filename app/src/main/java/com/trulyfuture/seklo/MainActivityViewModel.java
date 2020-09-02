package com.trulyfuture.seklo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.models.DegreeResults;
import com.trulyfuture.seklo.models.EducationResults;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.StudyFieldsResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.repository.SeekloRepository;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

public class MainActivityViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    private int userId;


    public LiveData<UserResults> userResults = new MutableLiveData<>();

    public LiveData<HrResults> hrResults = new MutableLiveData<>();
    public LiveData<DegreeResults> degreeResults=new MutableLiveData<>();
    public LiveData<StudyFieldsResults> studyFieldsResults=new MutableLiveData<>();
    public LiveData<EducationResults> educationResults=new MutableLiveData<>();

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
        getAllDegrees();
        getAllStudyFields();
        getAllUserEducation(userId);
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

    private void getAllDegrees(){degreeResults=seekloRepository.getAllDegrees();}

    private void getAllStudyFields(){studyFieldsResults=seekloRepository.getAllStudyFields();}

    private void getAllUserEducation(int id){educationResults=seekloRepository.getUserEducation(id); }
}
