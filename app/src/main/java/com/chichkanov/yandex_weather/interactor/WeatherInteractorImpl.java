package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class WeatherInteractorImpl implements WeatherInteractor {

    @Inject
    WeatherRepositoryImpl repository;

    public WeatherInteractorImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<CurrentWeather> getWeather() {
        return repository.getWeather();
    }

    @Override
    public Single<List<Forecast>> getForecasts() {
        return repository.getForecasts();
    }

    @Override
    public Maybe<Double> getCurrentWeatherFromDBbyId(int cityId) {
        return repository.getCurrentTempFromDBbyCityId(cityId);
    }

    @Override
    public Single<List<Forecast>> getForecastsFromInternet() {
        return repository.getForecastFromInternet();
    }

    @Override
    public Single<CurrentWeather> getCurrentWeatherFromInternet() {
        return repository.getCurrentWeatherFromInternet();
    }
}
