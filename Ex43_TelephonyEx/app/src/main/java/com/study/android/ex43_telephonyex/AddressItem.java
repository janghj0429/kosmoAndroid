package com.study.android.ex43_telephonyex;

import android.graphics.Bitmap;

public class AddressItem {

    private String name;
    private String telnum;
    private Bitmap resId;

    public AddressItem(String name, String telnum, Bitmap resId) {
        this.name = name;
        this.telnum = telnum;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public Bitmap getResId() {
        return resId;
    }

    public void setResId(Bitmap resId) {
        this.resId = resId;
    }
}
