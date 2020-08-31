package com.trulyfuture.seklo.screens.uploadCV;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.trulyfuture.seklo.repository.SeekloRepository;

public class UploadCVViewModel extends AndroidViewModel {
    private SeekloRepository seekloRepository;

    public UploadCVViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }





}
