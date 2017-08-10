package com.chichkanov.yandex_weather.ui.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.ui.about.AboutFragment;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.settings.SettingsFragment;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private NavigationManager navigationManager;

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

    void showChangeCityFragment() {
        navigationManager.navigateToAndAddBackStack(ChangeCityFragment.newInstance());
    }



}
