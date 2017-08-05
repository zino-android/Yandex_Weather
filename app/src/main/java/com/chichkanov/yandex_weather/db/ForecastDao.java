package com.chichkanov.yandex_weather.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

import io.reactivex.Flowable;

@android.arch.persistence.room.Dao
public interface ForecastDao {
    @Insert
    public void insertForecasts(List<Forecast> forecasts);

    @Query("SELECT * FROM forecasts WHERE dateTime >= :dateTime ORDER BY dateTime ASC")
    public Flowable<List<Forecast>> loadForecastByDateTime(long dateTime);
}
