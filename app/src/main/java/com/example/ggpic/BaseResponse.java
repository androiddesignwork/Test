package com.example.ggpic;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    private int code;
    private String msg;

    public final static int RESPONSE_SUCCESS = 0;

    @SerializedName(value = "data",alternate = {"img","acgurl","imgurl"})
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BaseResponse() {
    }

}
