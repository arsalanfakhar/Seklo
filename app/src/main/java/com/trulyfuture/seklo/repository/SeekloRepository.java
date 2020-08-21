package com.trulyfuture.seklo.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.database.retrofit.SeekloApiInterface;
import com.trulyfuture.seklo.models.ResultSeklo;
import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeekloRepository {

    private Application application;
    private SeekloApiInterface apiInterface;

    public SeekloRepository(Application application) {
        this.application = application;
        apiInterface = RetrofitService.getInterface();
    }

    public MutableLiveData<Results> createUser(Users user) {
        MutableLiveData<Results> data = new MutableLiveData<>();

        Call<ResultSeklo> resultsCall = apiInterface.createUser(user);

        resultsCall.enqueue(new Callback<ResultSeklo>() {
            @Override
            public void onResponse(Call<ResultSeklo> call, Response<ResultSeklo> response) {

                data.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResultSeklo> call, Throwable t) {
                Results result = new Results();
                result.setCode(-1);
                result.setMessage(t.getMessage());
                data.postValue(result);
            }
        });

        return data;
    }


}
