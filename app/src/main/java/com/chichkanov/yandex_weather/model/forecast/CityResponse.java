package com.chichkanov.yandex_weather.model.forecast;

import com.google.gson.annotations.SerializedName;



public class CityResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
