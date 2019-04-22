package com.study.android.ex45_sqlite2;

public class SingerItem {

    private String Name;
    private int age;
    private String mobile;

    public SingerItem(String name, int age, String mobile) {
        Name = name;
        this.age = age;
        this.mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
