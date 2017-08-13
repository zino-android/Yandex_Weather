package com.chichkanov.yandex_weather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ForecastResponse {
    @SerializedName("city")
    private CityResponse city;
    @SerializedName("list")
    private List<ForecastItemResponse> forecasts;

    public CityResponse getCity() {
        return city;
    }

    public List<ForecastItemResponse> getForecasts() {
        return forecasts;
    }
}
