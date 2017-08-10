package com.chichkanov.yandex_weather.ui.favorite_cities;

import com.arellomobile.mvp.MvpView;
import com.chichkanov.yandex_weather.model.CityMenu;

import java.util.List;


public interface FavoriteCitiesView extends MvpView {

    void showCities(List<CityMenu> cities);

}
