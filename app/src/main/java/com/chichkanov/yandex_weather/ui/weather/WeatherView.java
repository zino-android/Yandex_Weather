package com.chichkanov.yandex_weather.ui.weather;

import com.arellomobile.mvp.MvpView;
import com.chichkanov.yandex_weather.model.CurrentWeather;

interface WeatherView extends MvpView{
    void showLoading();
    void hideLoading();
    void showWeather(CurrentWeather weather);
}
