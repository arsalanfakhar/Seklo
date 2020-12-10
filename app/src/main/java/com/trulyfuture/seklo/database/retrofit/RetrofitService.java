package com.trulyfuture.seklo.database.retrofit;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String BASE_URL = "https://sekloinc.herokuapp.com/";

    public static HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static OkHttpClient client=new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(25, TimeUnit.SECONDS)
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    public static SeekloApiInterface getInterface(){
        return retrofit.create(SeekloApiInterface.class);
    }

}
