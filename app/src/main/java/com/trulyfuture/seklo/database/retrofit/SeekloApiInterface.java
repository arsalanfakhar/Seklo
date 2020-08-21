package com.trulyfuture.seklo.database.retrofit;

import com.trulyfuture.seklo.models.ResultSeklo;
import com.trulyfuture.seklo.models.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SeekloApiInterface {

    @POST("users")
    Call<ResultSeklo> createUser(@Body Users user);

    @POST("users/login")
    Call<ResultSeklo> loginUser(@Body Users user);


}
