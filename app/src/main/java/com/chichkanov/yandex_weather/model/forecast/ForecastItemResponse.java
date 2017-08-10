package com.chichkanov.yandex_weather.model.forecast;

import com.chichkanov.yandex_weather.model.current_weather.Weather;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ForecastItemResponse {
    @SerializedName("dt")
    private long dt;
    @SerializedName("temp")
    private Temp temp;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("speed")
    private double speed;
    @SerializedName("deg")
    private double deg;
    @SerializedName("clouds")
    private int clouds;
    @SerializedName("rain")
    private double rain;

    public long getDt() {
        return dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }

    public int getClouds() {
        return clouds;
    }

    public double getRain() {
        return rain;
    }
}
