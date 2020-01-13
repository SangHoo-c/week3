package com.example.myapplication.server;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AccountClient {
    private static Retrofit instance;

    public static Retrofit getInstance() { // parameter로 url을 구분해야 할 수도 있다.
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(IRetrofit.URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}