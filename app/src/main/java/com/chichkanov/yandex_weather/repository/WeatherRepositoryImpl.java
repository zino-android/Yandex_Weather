package com.chichkanov.yandex_weather.repository;

import android.util.Log;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.chichkanov.yandex_weather.model.forecast.ForecastItemResponse;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    @Inject
    WeatherApi weatherApi;

    @Inject
    Settings settings;

    @Inject
    IOtools iotools;

    @Inject
    WeatherDatabase database;

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
    public Observable<List<Forecast>> getForecasts(String cityName) {
        String locale = WeatherUtils.getLocale();

        Observable<List<Forecast>> forecastInternet = weatherApi
                .getForecasts(cityName, Constants.API_KEY, locale, "metric")
                .map(this::transformForecastResponseToList)
                .doOnNext(forecasts -> {
                   database.forecastDao().insertForecasts(forecasts);
                });



        return forecastInternet;
    }

    private List<Forecast> transformForecastResponseToList(ForecastResponse forecastResponse) {
        List<Forecast> forecasts = new ArrayList<>(forecastResponse.getForecasts().size());

        for (ForecastItemResponse item : forecastResponse.getForecasts()) {
            Forecast forecast = new Forecast();
            forecast.setTitle(item.getWeather().get(0).getMain());
            forecast.setDescription(item.getWeather().get(0).getDescription());
            forecast.setIcon(item.getWeather().get(0).getIcon());
            forecast.setDayTemp(item.getTemp().getDay());
            forecast.setEveningTemp(item.getTemp().getEve());
            forecast.setMorningTemp(item.getTemp().getMorn());
            forecast.setNightTemp(item.getTemp().getNight());
            forecast.setMinTemp(item.getTemp().getMin());
            forecast.setMaxTemp(item.getTemp().getMax());
            forecast.setDateTime(item.getDt());
            forecast.setClouds(item.getClouds());
            forecast.setHumidity(item.getHumidity());
            forecast.setPressure(item.getPressure());
            forecast.setRain(item.getRain());
            forecast.setWindSpeed(item.getSpeed());
            forecast.setWindDegree(item.getDeg());
            forecast.setCityId(forecastResponse.getCity().getId());
            forecasts.add(forecast);
        }

        return forecasts;
    }
}


