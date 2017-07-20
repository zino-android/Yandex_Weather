package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    @Inject
    WeatherRepositoryImpl repository;

    public WeatherInteractorImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {
        return repository.getWeather(cityName);
    }
}
