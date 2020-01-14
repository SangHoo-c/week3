package com.example.myapplication.server;

import java.io.Serializable;

public class MbtiData implements Serializable {
    private String type;
    private String ratio_I;
    private String ratio_N;
    private String ratio_T;
    private String ratio_J;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getRatio_I() {
        return ratio_I;
    }

    public void setRatio_I(String ratio_I) {
        this.ratio_I = ratio_I;
    }

    public String getRatio_N() {
        return ratio_N;
    }

    public void setRatio_N(String ratio_N) {
        this.ratio_N = ratio_N;
    }

    public String getRatio_T() {
        return ratio_T;
    }

    public void setRatio_T(String ratio_T) {
        this.ratio_T = ratio_T;
    }

    public String getRatio_J() {
        return ratio_J;
    }

    public void setRatio_J(String ratio_J) {
        this.ratio_J = ratio_J;
    }
}
