package com.example.ggpic;

public class Explorer {
    private String Text;//图片类型
    private String PicUrl;//类型封面

    public String getText() {
        return Text;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public void setText(String text) {
        Text = text;
    }
}
