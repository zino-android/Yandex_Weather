package com.chichkanov.yandex_weather.ui.main;

import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.ui.about.AboutFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.settings.SettingsFragment;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

class MainPresenter extends MvpPresenter<MainView> {

    private NavigationManager navigationManager;

    MainPresenter() {

    }

    void addNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    void showWeatherFragment() {
        navigationManager.navigateTo(WeatherFragment.newInstance());
    }

    void showSettingsFragment() {
        navigationManager.navigateTo(SettingsFragment.newInstance());
    }

    void showAboutFragment() {
        navigationManager.navigateTo(AboutFragment.newInstance());
    }


}
