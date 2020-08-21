package com.trulyfuture.seklo.database.retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String BASE_URL = "https://seklonew.uc.r.appspot.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static SeekloApiInterface getInterface(){
        return retrofit.create(SeekloApiInterface.class);
    }
}
