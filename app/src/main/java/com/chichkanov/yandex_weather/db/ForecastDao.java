package com.chichkanov.yandex_weather.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertForecasts(List<Forecast> forecasts);

    @Query("SELECT * FROM forecasts WHERE dateTime >= :dateTime ORDER BY dateTime ASC")
    public Single<List<Forecast>> loadForecastByDateTime(long dateTime);

    @Query("DELETE FROM forecasts WHERE dateTime <= :dateTime")
    public void deleteOldForecasts(long dateTime);
}
