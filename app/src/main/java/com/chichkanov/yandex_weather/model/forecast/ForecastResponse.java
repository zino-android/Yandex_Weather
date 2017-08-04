package com.chichkanov.yandex_weather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ForecastResponse {
    @SerializedName("city")
    private City city;
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("cnt")
    private int cnt;
    @SerializedName("list")
    private List<Forecast> forecasts;

    public City getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }
}
