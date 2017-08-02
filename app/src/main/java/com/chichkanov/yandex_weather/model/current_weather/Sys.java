package com.chichkanov.yandex_weather.model.current_weather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Sys {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("sunrise")
    @Expose
    private int sunrise;
    @SerializedName("sunset")
    @Expose
    private int sunset;

    public String getCountry() {
        return country;
    }

    public double getMessage() {
        return message;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

}
