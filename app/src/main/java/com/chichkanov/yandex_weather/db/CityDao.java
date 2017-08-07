package com.chichkanov.yandex_weather.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.chichkanov.yandex_weather.model.City;

import io.reactivex.Maybe;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertCity(City city);

    @Query("SELECT * FROM cities WHERE isSelected = 1")
    public Maybe<City> loadCurrentCity();

    @Query("UPDATE cities SET isSelected = 0 WHERE isSelected = 1 AND id != :currentSelectedId")
    public void updateSelectedCity(long currentSelectedId);

    @Query("UPDATE cities SET cityId = :cityId WHERE isSelected = 1")
    public void updateSelectedCityId(int cityId);

    @Query("DELETE FROM cities WHERE cityId = :cityId AND isSelected != 1")
    public void deleteCityById(int cityId);

}
