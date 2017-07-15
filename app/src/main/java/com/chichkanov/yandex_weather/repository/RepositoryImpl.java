package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.utils.Constants;

import java.util.Locale;

import io.reactivex.Observable;

public class RepositoryImpl implements Repository {

    private WeatherApi weatherApi;

    public RepositoryImpl(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {
        String locale = Locale.getDefault().getLanguage().equals("ru") ? "ru" : "en";
        return weatherApi
                .getWeather(cityName, Constants.API_KEY, locale);
    }
}
