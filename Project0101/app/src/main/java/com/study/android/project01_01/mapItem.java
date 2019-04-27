package com.study.android.project01_01;

public class mapItem {
    private String address;
    private String title;
    private String mapX;
    private String mapY;

    public mapItem(String address, String title, String mapX, String mapY) {
        this.address = address;
        this.title = title;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMapX() {
        return mapX;
    }

    public void setMapX(String mapX) {
        this.mapX = mapX;
    }

    public String getMapY() {
        return mapY;
    }

    public void setMapY(String mapY) {
        this.mapY = mapY;
    }
}
