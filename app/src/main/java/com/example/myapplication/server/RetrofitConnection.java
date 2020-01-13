package com.example.myapplication.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.249.19.250:7680/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    public RetrofitInterface server = retrofit.create(RetrofitInterface.class);
}
