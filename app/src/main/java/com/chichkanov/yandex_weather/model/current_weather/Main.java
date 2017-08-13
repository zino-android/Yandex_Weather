package com.chichkanov.yandex_weather.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;

    public int getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

}
