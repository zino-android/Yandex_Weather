package com.chichkanov.yandex_weather.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("deg")
    @Expose
    private double deg;
    @SerializedName("speed")
    @Expose
    private double speed;

    public double getDeg() {
        return deg;
    }

    public double getSpeed() {
        return speed;
    }
}
