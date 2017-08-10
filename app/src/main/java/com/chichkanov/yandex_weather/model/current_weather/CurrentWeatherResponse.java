package com.chichkanov.yandex_weather.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {

    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("cod")
    @Expose
    private int cod;
    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    public String getBase() {
        return base;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public int getCod() {
        return cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public int getDt() {
        return dt;
    }

    public int getId() {
        return id;
    }

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public Sys getSys() {
        return sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

}
