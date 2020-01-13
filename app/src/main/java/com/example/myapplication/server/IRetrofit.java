package com.example.myapplication.server;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IRetrofit {
    String URL = "http://192.249.19.250:7880";
    String URL2 = "http://192.249.19.251:8980";

    // name, phone_number, photo_id
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("photo_uri") String photo_uri,
                                    @Field("name") String name,
                                    @Field("phone_number") String phone_number,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("address") String address);
    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    // get all contacts of account
    @POST("getContact")
    @FormUrlEncoded
    Observable<String> getContact(@Field("account") String account);

    // create new contact of account
    @POST("putContact")
    @FormUrlEncoded
    Observable<String> putContact(@Field("account") String account,
                                  @Field("name") String name,
                                  @Field("phone_number") String phone_number,
                                  @Field("photo_id") int photo_id);

    // update a contact of account
    @POST("updateContact")
    @FormUrlEncoded
    Observable<String> updateContact(@Field("account") String account,
                                     @Field("previous_name") String previous_name,
                                     @Field("previous_phone_number") String previous_phone_number,
                                     @Field("previous_photo_id") int photo_id,
                                     @Field("new_name") String new_name,
                                     @Field("new_phone_number") String new_phone_number,
                                     @Field("new_photo_id") int new_photo_id);

    // delete a contact of account
    @POST("deleteContact")
    @FormUrlEncoded
    Observable<String> deleteContact(@Field("account") String account,
                                     @Field("name") String name,
                                     @Field("phone_number") String phone_number,
                                     @Field("photo_id") int photo_id);

    @POST("getUserInfo")
    @FormUrlEncoded
    Observable<String> getUserInfo(@Field("region") String region);
}