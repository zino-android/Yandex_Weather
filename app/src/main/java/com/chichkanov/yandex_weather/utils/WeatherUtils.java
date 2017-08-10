package com.chichkanov.yandex_weather.utils;

import android.support.annotation.NonNull;

import com.chichkanov.yandex_weather.R;

import java.util.Locale;

public class WeatherUtils {

    @NonNull
    public static String getLocale() {
        return Locale.getDefault().getLanguage().equals("ru") ? "ru" : "en";
    }

    @NonNull
    public static String formatTemperature(double temp) {
        if (temp > 0) {
            return String.format("+%.0f°", temp);
        } else {
            return String.format("%.0f°", temp);
        }
    }

    public static int getImageIdByName(@NonNull String name) {
        switch (name) {
            case "01d":
            case "01n":
                return R.drawable.sun;
            case "02d":
            case "02n":
                return R.drawable.cloud_sun;
            case "03d":
            case "03n":
                return R.drawable.cloud;
            case "04d":
            case "04n":
                return R.drawable.cloud;
            case "09d":
            case "09n":
                return R.drawable.shower_rain;
            case "10d":
            case "10n":
                return R.drawable.rain;
            case "11d":
            case "11n":
                return R.drawable.thunderstorm;
            case "13d":
            case "13n":
                return R.drawable.mist;
            default:
                return R.drawable.sun;
        }
    }
}
