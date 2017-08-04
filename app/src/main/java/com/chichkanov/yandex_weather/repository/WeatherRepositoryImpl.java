package com.chichkanov.yandex_weather.repository;

import android.util.Log;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    @Inject
    WeatherApi weatherApi;

    @Inject
    Settings settings;

    @Inject
    IOtools iotools;

    public WeatherRepositoryImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {

        String locale = WeatherUtils.getLocale();

        Observable<CurrentWeather> weatherDb = null;
        CurrentWeather currentWeather = iotools.getCurrentWeather();
        if (currentWeather != null) {
            weatherDb = Observable.just(currentWeather);
            Log.i("Repository", "Cache exist");
        }

        Observable<CurrentWeather> weatherInternet = weatherApi
                .getWeather(cityName, Constants.API_KEY, locale, "metric")
                .doOnNext(f -> {
                    settings.saveLastUpdateTime();
                    iotools.saveCurrentWeather(f);
                });

        if (weatherDb == null) {
            return weatherInternet;
        } else {
            return Observable.concat(weatherDb, weatherInternet);
        }
    }

    @Override
    public Observable<ForecastResponse> getForecasts(String cityName) {
        String locale = WeatherUtils.getLocale();

        Observable<ForecastResponse> forecastInternet = weatherApi
                .getForecasts(cityName, Constants.API_KEY, locale, "metric");


        return forecastInternet;
    }
}


