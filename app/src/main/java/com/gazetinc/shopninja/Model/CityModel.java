package com.gazetinc.shopninja.Model;

public class CityModel
{
    private String name;
    private int url;

    public CityModel(String name, int url) {
        this.name = name;
        this.url = url;
    }

    public CityModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
