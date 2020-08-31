package com.trulyfuture.seklo.database.retrofit;

import com.trulyfuture.seklo.models.HrResults;
import com.trulyfuture.seklo.models.SekloResults;
import com.trulyfuture.seklo.models.UserResults;
import com.trulyfuture.seklo.models.Users;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SeekloApiInterface {



    @POST("users")
    Call<SekloResults> createUser(@Body Users user);

    @POST("users/login")
    Call<SekloResults> loginUser(@Body Users user);

    @GET("users/{id}")
    Call<UserResults> getUserById(@Path("id") int userId);

    @GET("hr")
    Call<HrResults> getAllHr();

    @PATCH("users/{id}")
    Call<SekloResults> updateUserDetails(@Body Users user, @Path("id") int userId);

    @PATCH("users/{id}")
    Call<SekloResults> updateUserImage(
            @Body Map<String, String> userMap,
            @Path("id") int userId
    );


}
