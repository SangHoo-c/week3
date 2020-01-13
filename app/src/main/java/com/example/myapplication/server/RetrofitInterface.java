package com.example.myapplication.server;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("login/user/:id")
    Call<LoginData> getUser(@Path("id") String id);

    @POST("login/login")
    Call<LoginData> login(@Body LoginData user);

    @POST("login/join")
    Call<LoginData> join(@Body LoginData user);
}
