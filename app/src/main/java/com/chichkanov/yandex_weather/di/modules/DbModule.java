package com.chichkanov.yandex_weather.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.chichkanov.yandex_weather.db.WeatherDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Singleton
    @Provides
    WeatherDatabase provideWeatherDatabase(Context context) {
        return Room.databaseBuilder(context, WeatherDatabase.class, "weather.db").build();
    }
}
