package com.chichkanov.yandex_weather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ForecastResponse {
    @SerializedName("city")
    private CityResponse city;
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("cnt")
    private int cnt;
    @SerializedName("list")
    private List<ForecastItemResponse> forecasts;

    public CityResponse getCity() {
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

    public List<ForecastItemResponse> getForecasts() {
        return forecasts;
    }
}
