package com.chichkanov.yandex_weather.di.modules;

import android.content.Context;

import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    Settings provideSettings() {
        return new Settings(context);
    }

    @Singleton
    @Provides
    IOtools provideIotools() {
        return new IOtools(context);
    }
}
