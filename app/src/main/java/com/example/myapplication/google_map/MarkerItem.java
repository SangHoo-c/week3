package com.example.myapplication.google_map;

public class MarkerItem {
    String User_name;
    String User_mbti;

    public MarkerItem(String user_name, String user_mbti) {
        User_name = user_name;
        User_mbti = user_mbti;
    }

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
}
