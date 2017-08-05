package com.chichkanov.yandex_weather.ui.weather;

import com.arellomobile.mvp.MvpView;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;

import java.util.List;

interface WeatherView extends MvpView{
    void showLoading();
    void hideLoading();
    void showError();
    void showWeather(CurrentWeather weather, String lastUpdateDate);
    void showCityName(String cityName);

    void showForecast(List<Forecast> forecasts);
}
