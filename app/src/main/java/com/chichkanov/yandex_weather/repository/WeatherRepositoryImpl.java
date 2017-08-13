package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeatherResponse;
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

    private WeatherApi weatherApi;

    private Settings settings;

    private WeatherDatabase database;

    @Inject
    public WeatherRepositoryImpl(WeatherApi weatherApi, Settings settings, WeatherDatabase database) {
        this.weatherApi = weatherApi;
        this.settings = settings;
        this.database = database;
    }

    @Override
    public Flowable<CurrentWeather> getWeather() {
        Single<CurrentWeather> weatherDb = database.cityDao().loadCurrentCity().toSingle()
                .flatMap(c -> database.currentWeatherDao()
                        .loadCurrentWeatherByCityId(c.getCityId()))
                .onErrorResumeNext(getCurrentWeatherFromInternet());

        return Single.concat(weatherDb, getCurrentWeatherFromInternet());

    }

    @Override
    public Single<CurrentWeather> getCurrentWeatherFromInternet() {
        String locale = WeatherUtils.getLocale();

        return database.cityDao().loadCurrentCity().toSingle()
                .flatMap(city -> weatherApi.getWeather(city.getDescription(), Constants.API_KEY, locale, "metric"))
                .map(this::transformCurrentWeatherResponseToCurrentWeather)
                .doOnSuccess(weather -> {
                    database.cityDao().updateSelectedCityId(weather.getCityId());
                    settings.saveLastUpdateTime();
                    database.currentWeatherDao().insertCurrentWeather(weather);
                });
    }

    @Override
    public Flowable<List<Forecast>> getForecasts() {
        Maybe<List<Forecast>> forecastDb = database.cityDao().loadCurrentCity()
                .flatMap(city -> database.forecastDao()
                        .loadForecastByDateTimeAndCityId(System.currentTimeMillis() / 1000, city.getCityId()).toMaybe())
                .onErrorResumeNext(getForecastFromInternet().toMaybe());

        return Maybe.concat(forecastDb, getForecastFromInternet().toMaybe());
    }

    @Override
    public Single<List<Forecast>> getForecastFromInternet() {
        String locale = WeatherUtils.getLocale();

        return database.cityDao().loadCurrentCity().toSingle()
                .flatMap(city -> weatherApi.getForecasts(city.getDescription(), Constants.API_KEY, locale, "metric"))
                .map(this::transformForecastResponseToList)
                .doOnSuccess(forecasts -> {
                    database.forecastDao().insertForecasts(forecasts);
                    database.forecastDao().deleteOldForecasts(System.currentTimeMillis() / 1000);
                });
    }

    @Override
    public Maybe<Double> getCurrentTempFromDBbyCityId(int cityId) {
        return database.currentWeatherDao().loadCurrentTempByCityId(cityId).toMaybe();
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

    private CurrentWeather transformCurrentWeatherResponseToCurrentWeather(CurrentWeatherResponse currentWeatherResponse) {
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
    }
}