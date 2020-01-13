package com.example.myapplication.server;

import java.io.Serializable;
import java.util.List;

public class LoginData implements Serializable {
    private String id;
    private String name;
    private String passwd;
    //private String profile;
    private String address;
    private List<MbtiData> mbti;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MbtiData> getMbti() {
        return mbti;
    }

    public void setMbti(List<MbtiData> mbti) {
        this.mbti = mbti;
    }
}
