package com.chichkanov.yandex_weather.ui.weather;

import com.arellomobile.mvp.MvpView;
import com.chichkanov.yandex_weather.model.CurrentWeather;

interface WeatherView extends MvpView{
    void showLoading();
    void hideLoading();
    void showError();
    void showWeather(CurrentWeather weather, String lastUpdateDate);
    void showCityName(String cityName);
}
