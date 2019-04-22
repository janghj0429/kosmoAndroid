package com.study.android.ex20_1_exam;

public class ClassItem {

    private String gender;
    private String name;
    private String telnum;
    private int resId;

    public ClassItem(String gender, String name, String telnum, int resId) {
        this.gender = gender;
        this.name = name;
        this.telnum = telnum;
        this.resId = resId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
