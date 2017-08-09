package com.chichkanov.yandex_weather.ui.main;

import com.arellomobile.mvp.MvpView;
import com.chichkanov.yandex_weather.model.CityMenu;

import java.util.List;

interface MainView extends MvpView {

    void showCities(List<CityMenu> cities);

}
