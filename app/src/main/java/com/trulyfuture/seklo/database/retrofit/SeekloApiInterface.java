package com.trulyfuture.seklo.database.retrofit;

import com.trulyfuture.seklo.models.LoginSignUpResults;
import com.trulyfuture.seklo.models.Results;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.models.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SeekloApiInterface {

    @POST("users")
    Call<LoginSignUpResults> createUser(@Body Users user);

    @POST("users/login")
    Call<LoginSignUpResults> loginUser(@Body Users user);

    @POST("users/{id}")
    Call<UserResults> getUserById(@Path("id") int userId);

}
