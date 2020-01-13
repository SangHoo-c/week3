package com.example.myapplication.server;

import java.io.Serializable;

public class MbtiData implements Serializable {
    private String type;
    private Double ratio_I;
    private Double ratio_N;
    private Double ratio_T;
    private Double ratio_J;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getRatio_I() {
        return ratio_I;
    }

    public void setRatio_I(Double ratio_I) {
        this.ratio_I = ratio_I;
    }

    public Double getRatio_N() {
        return ratio_N;
    }

    public void setRatio_N(Double ratio_N) {
        this.ratio_N = ratio_N;
    }

    public Double getRatio_T() {
        return ratio_T;
    }

    public void setRatio_T(Double ratio_T) {
        this.ratio_T = ratio_T;
    }

    public Double getRatio_J() {
        return ratio_J;
    }

    public void setRatio_J(Double ratio_J) {
        this.ratio_J = ratio_J;
    }
}
