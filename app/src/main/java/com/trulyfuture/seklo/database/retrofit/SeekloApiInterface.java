package com.trulyfuture.seklo.database.retrofit;

import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.models.UsersShort;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SeekloApiInterface {

    @POST("users")
    Call<Results> createUser(@Body UsersShort user);




}
