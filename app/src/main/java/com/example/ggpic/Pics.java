package com.example.ggpic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pics {

    public String getmPicUrl() {
        return mPicUrl;
    }

    public void setmPicUrl(String mPicUrl) {
        this.mPicUrl = mPicUrl;
    }

    private String mPicUrl;
}

