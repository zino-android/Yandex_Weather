package com.chichkanov.yandex_weather.model.current_weather;


import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("country")
    private String country;
    @SerializedName("message")
    private double message;
    @SerializedName("sunrise")
    private long sunrise;
    @SerializedName("sunset")
    private long sunset;

    public String getCountry() {
        return country;
    }

    public double getMessage() {
        return message;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

}
