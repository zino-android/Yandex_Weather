package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.forecast.ForecastItemResponse;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.Settings;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    @Inject
    WeatherApi weatherApi;

    @Inject
    Settings settings;


    @Inject
    WeatherDatabase database;

    public WeatherRepositoryImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<CurrentWeather> getWeather(String cityName) {

        String locale = WeatherUtils.getLocale();

        Maybe<CurrentWeather> weatherDb = database.currentWeatherDao().loadCurrentWeatherByCityId(524901);

        Single<CurrentWeather> weatherInternet = weatherApi
                .getWeather(cityName, Constants.API_KEY, locale, "metric")

                .map(currentWeatherResponse -> {
                    CurrentWeather currentWeather = new CurrentWeather();
                    currentWeather.setCityId(currentWeatherResponse.getId());
                    currentWeather.setWindDegree(currentWeatherResponse.getWind().getDeg());
                    currentWeather.setWindSpeed(currentWeatherResponse.getWind().getSpeed());
                    currentWeather.setTitle(currentWeatherResponse.getWeather().get(0).getMain());
                    currentWeather.setDescription(currentWeatherResponse.getWeather().get(0).getDescription());
                    currentWeather.setIcon(currentWeatherResponse.getWeather().get(0).getIcon());
                    currentWeather.setPressure(currentWeatherResponse.getMain().getPressure());
                    currentWeather.setHumidity(currentWeatherResponse.getMain().getHumidity());
                    currentWeather.setClouds(currentWeatherResponse.getClouds().getAll());
                    currentWeather.setMaxTemp(currentWeatherResponse.getMain().getTempMax());
                    currentWeather.setMinTemp(currentWeatherResponse.getMain().getTempMin());
                    currentWeather.setTemp(currentWeatherResponse.getMain().getTemp());
                    currentWeather.setDateTime(currentWeatherResponse.getDt());
                    currentWeather.setSunrise(currentWeatherResponse.getSys().getSunrise());
                    currentWeather.setSunset(currentWeatherResponse.getSys().getSunset());
                    return currentWeather;
                })
                .map(f -> {
                    settings.saveLastUpdateTime();
                    database.currentWeatherDao().insertCurrentWeather(f);
                    return f;
                });

        if (weatherDb == null) {
            return weatherInternet.toFlowable();
        } else {
            return Flowable.concat(weatherDb.toFlowable(), weatherInternet.toFlowable());
        }
    }

    @Override
    public Flowable<List<Forecast>> getForecasts(String cityName) {
        String locale = WeatherUtils.getLocale();


        Single<List<Forecast>> forecastDb = database.forecastDao().loadForecastByDateTime(System.currentTimeMillis() / 1000);

        Single<List<Forecast>> forecastInternet = weatherApi
                .getForecasts(cityName, Constants.API_KEY, locale, "metric")
                .map(this::transformForecastResponseToList)
                .doOnSuccess(forecasts -> {
                   database.forecastDao().insertForecasts(forecasts);
                });

        if (forecastDb == null) {
            return forecastInternet.toFlowable();
        } else {
            return Single.concat(forecastDb, forecastInternet);
        }
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


