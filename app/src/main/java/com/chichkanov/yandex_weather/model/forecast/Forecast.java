package com.chichkanov.yandex_weather.model.forecast;

import com.chichkanov.yandex_weather.model.current_weather.Clouds;
import com.chichkanov.yandex_weather.model.current_weather.Main;
import com.chichkanov.yandex_weather.model.current_weather.Weather;
import com.chichkanov.yandex_weather.model.current_weather.Wind;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Forecast {
    @SerializedName("dt")
    public int dateTime;
    @SerializedName("main")
    public Main main;
    @SerializedName("weather")
    public List<Weather> weather;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("dt_txt")
    public String dtTxt;

    public int getDateTime() {
        return dateTime;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public String getDtTxt() {
        return dtTxt;
    }
}
