package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeatherResponse;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    @Inject
    WeatherRepositoryImpl repository;

    public WeatherInteractorImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<CurrentWeather> getWeather(String cityName) {
        return repository.getWeather(cityName);
    }

    @Override
    public Flowable<List<Forecast>> getForecasts(String cityName) {
        return repository.getForecasts(cityName);
    }
}
