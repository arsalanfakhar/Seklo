package com.trulyfuture.seklo.screens.companyDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trulyfuture.seklo.models.CompanyResults;
import com.trulyfuture.seklo.repository.SeekloRepository;

public class CompanyDetailViewModel extends AndroidViewModel {

    private SeekloRepository seekloRepository;
    public CompanyDetailViewModel(@NonNull Application application) {
        super(application);
        seekloRepository=new SeekloRepository(application);
    }

    public LiveData<CompanyResults> getCompanyById(int companyId) {
        return seekloRepository.getCompanyById(companyId);
    }
}
