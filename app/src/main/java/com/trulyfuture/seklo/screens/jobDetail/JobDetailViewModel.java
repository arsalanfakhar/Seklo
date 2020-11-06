package com.trulyfuture.seklo.screens.jobDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trulyfuture.seklo.models.CvExistsResults;
import com.trulyfuture.seklo.models.JobApply;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.repository.SeekloRepository;

public class JobDetailViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;

    public JobDetailViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<CvExistsResults> cvExists(int userId){
        return seekloRepository.checkCv(userId);
    }

    public LiveData<SekloResults> applyForJob(JobApply jobApplyData){
        return seekloRepository.applyForJob(jobApplyData);
    }

}
