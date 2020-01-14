package com.example.myapplication.google_map;

import java.io.Serializable;

public class Location_cus implements Serializable {
    private String userId;
    private String User_name = "";
    private String User_mbti = "";
    private Double Latitude;
    private Double Longitude;


    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_mbti() {
        return User_mbti;
    }

    public void setUser_mbti(String user_mbti) {
        User_mbti = user_mbti;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
