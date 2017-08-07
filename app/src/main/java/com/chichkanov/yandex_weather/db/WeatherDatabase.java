package com.chichkanov.yandex_weather.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;

@Database(version = 1, entities = {Forecast.class, CurrentWeather.class, City.class})
public abstract class WeatherDatabase extends RoomDatabase {

    abstract public ForecastDao forecastDao();

    abstract public CurrentWeatherDao currentWeatherDao();

    abstract public CityDao cityDao();
}
