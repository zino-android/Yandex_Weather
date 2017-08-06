package com.chichkanov.yandex_weather.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.chichkanov.yandex_weather.model.CurrentWeather;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCurrentWeather(CurrentWeather currentWeather);

    @Query("SELECT * FROM current_weather WHERE cityId = :cityId LIMIT 1")
    public Maybe<CurrentWeather> loadCurrentWeatherByCityId(int cityId);
}
