package com.trulyfuture.seklo.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.trulyfuture.seklo.database.retrofit.RetrofitService;
import com.trulyfuture.seklo.database.retrofit.SeekloApiInterface;
import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.LoginSignUpResults;
import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.UserResults;
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

        Call<LoginSignUpResults> resultsCall = apiInterface.createUser(user);

        resultsCall.enqueue(new Callback<LoginSignUpResults>() {
            @Override
            public void onResponse(Call<LoginSignUpResults> call, Response<LoginSignUpResults> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    data.postValue(response.body().getResults());
                }
                else
                    Toast.makeText(application.getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginSignUpResults> call, Throwable t) {
                Results result = new Results();
                result.setCode(-1);
                result.setMessage(t.getMessage());
                data.postValue(result);
            }
        });

        return data;
    }

    public MutableLiveData<Results> loginUser(Users user) {
        MutableLiveData<Results> data = new MutableLiveData<>();

        Call<LoginSignUpResults> resultsCall = apiInterface.loginUser(user);

        resultsCall.enqueue(new Callback<LoginSignUpResults>() {
            @Override
            public void onResponse(Call<LoginSignUpResults> call, Response<LoginSignUpResults> response) {
                if(response.isSuccessful() && response.body()!=null)
                    data.postValue(response.body().getResults());
                else
                    Toast.makeText(application.getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginSignUpResults> call, Throwable t) {
                Results result = new Results();
                result.setCode(-1);
                result.setMessage(t.getMessage());
                data.postValue(result);
            }
        });

        return data;
    }

    public MutableLiveData<UserResults> getUserById(int id) {
        MutableLiveData<UserResults> data = new MutableLiveData<>();

        Call<UserResults> userResultsCall = apiInterface.getUserById(id);

        userResultsCall.enqueue(new Callback<UserResults>() {
            @Override
            public void onResponse(Call<UserResults> call, Response<UserResults> response) {

                if (response.isSuccessful() && response.body()!=null) {
                    data.postValue(response.body());
                }
                else
                    Toast.makeText(application.getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserResults> call, Throwable t) {
                UserResults results = new UserResults();
                results.setCode(-1);
                data.postValue(results);
            }
        });
        return data;
    }

    public MutableLiveData<HrResults> getAllHr(){
        MutableLiveData<HrResults> data=new MutableLiveData<>();
        Call<HrResults> hrResultsCall=apiInterface.getAllHr();

        hrResultsCall.enqueue(new Callback<HrResults>() {
            @Override
            public void onResponse(Call<HrResults> call, Response<HrResults> response) {
                if(response.isSuccessful() && response.body()!=null)
                    data.postValue(response.body());
                else
                    Toast.makeText(application.getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HrResults> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }
}
