package com.study.android.rectest;

public class SingerItem {

    private String name;
    private String age;
    private String str;

    public SingerItem(String name, String age, String str) {
        this.name = name;
        this.age = age;
        this.str = str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
