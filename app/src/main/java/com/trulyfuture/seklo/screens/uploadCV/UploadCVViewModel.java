package com.trulyfuture.seklo.screens.uploadCV;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.repository.SeekloRepository;

import java.util.Map;

public class UploadCVViewModel extends AndroidViewModel {
    private SeekloRepository seekloRepository;

    public UploadCVViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<SekloResults> addUserResume(Map<String,Object> resumeMap){
        return seekloRepository.addUserResume(resumeMap);
    }

    public LiveData<SekloResults> updateUserResume(Map<String,Object> resumeMap,int userId){
        return seekloRepository.updateResume(resumeMap,userId);
    }
}
