package com.chichkanov.yandex_weather.repository;

import android.util.Log;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    @Inject
    WeatherApi weatherApi;

    public WeatherRepositoryImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {
        String locale = Locale.getDefault().getLanguage().equals("ru") ? "ru" : "en";

        Observable<CurrentWeather> weatherDb = null;
        CurrentWeather currentWeather = IOtools.getCurrentWeather();
        if (currentWeather != null) {
            weatherDb = Observable.just(currentWeather);
            Log.i("Repository", "Cache exist");
        }

        Observable<CurrentWeather> weatherInternet = weatherApi
                .getWeather(cityName, Constants.API_KEY, locale, "metric")
                .doOnNext(f -> {
                    Settings.saveLastUpdateTime();
                    IOtools.saveCurrentWeather(f);
                });

        if (weatherDb == null) {
            return weatherInternet;
        } else {
            return Observable.concat(weatherDb, weatherInternet);
        }
    }
}


