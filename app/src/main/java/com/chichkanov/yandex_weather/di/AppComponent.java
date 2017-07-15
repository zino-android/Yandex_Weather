package com.chichkanov.yandex_weather.di;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.di.modules.NetworkModule;

import dagger.Component;

@Component(modules = {NetworkModule.class})
public interface AppComponent {
    void inject(App app);
}
