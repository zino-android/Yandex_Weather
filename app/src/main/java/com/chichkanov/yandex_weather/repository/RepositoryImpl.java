package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.utils.Constants;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RepositoryImpl implements Repository {

    @Inject
    WeatherApi weatherApi;

    public RepositoryImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {
        String locale = Locale.getDefault().getLanguage().equals("ru") ? "ru" : "en";
        return weatherApi
                .getWeather(cityName, Constants.API_KEY, locale);
    }
}
