package com.thereza.cmed_task02.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phones {
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
