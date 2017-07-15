package com.chichkanov.yandex_weather;

import android.app.Application;

import com.chichkanov.yandex_weather.di.AppComponent;
import com.chichkanov.yandex_weather.di.DaggerAppComponent;
import com.chichkanov.yandex_weather.di.modules.InteractorModule;
import com.chichkanov.yandex_weather.di.modules.NetworkModule;
import com.chichkanov.yandex_weather.di.modules.RepositoryModule;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent
                .builder()
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .interactorModule(new InteractorModule())
                .build();
        component.inject(this);
    }

    public static AppComponent getComponent() {
        return component;
    }


}
