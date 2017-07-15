package com.chichkanov.yandex_weather;

import android.app.Application;
import android.util.Log;

import com.chichkanov.yandex_weather.di.AppComponent;
import com.chichkanov.yandex_weather.di.DaggerAppComponent;

import java.util.Locale;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
        getComponent().inject(this);
    }

    public static AppComponent getComponent() {
        return component;
    }
}
