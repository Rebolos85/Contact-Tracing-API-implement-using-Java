package com.contact.tracing.adapter.retrofit;

import com.contact.tracing.service.UserService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUserClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitUserClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://poms-router.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)

                    .build();

        }

        return retrofit;
    }



    public static Retrofit getRetrofitLoginClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://poms-router.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }







}
