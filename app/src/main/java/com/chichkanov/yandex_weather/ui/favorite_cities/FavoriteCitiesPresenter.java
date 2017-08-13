package com.chichkanov.yandex_weather.ui.favorite_cities;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractor;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;

@InjectViewState
public class FavoriteCitiesPresenter extends MvpPresenter<FavoriteCitiesView> {

    private ChangeCityInteractor changeCityInteractor;

    private WeatherInteractor weatherInteractor;

    private NavigationManager navigationManager;

    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    @Inject
    public FavoriteCitiesPresenter(ChangeCityInteractor changeCityInteractor, WeatherInteractor weatherInteractor,
                                   Scheduler io, Scheduler main) {
        this.changeCityInteractor = changeCityInteractor;
        this.weatherInteractor = weatherInteractor;
        this.ioScheduler = io;
        this.mainScheduler = main;
    }

    public void onCitySelectedChanged(int cityId) {
        changeCityInteractor.setCitySelected(cityId);
        showWeatherFragment();
    }

    public void loadCities() {
        changeCityInteractor.getCities()
                .map(list -> {
                    ArrayList<CityMenu> cityMenus = new ArrayList<CityMenu>(list.size());
                    for (City city : list) {

                       weatherInteractor
                                .getCurrentWeatherFromDBbyId(city.getCityId())
                                .subscribeOn(ioScheduler)
                                .onErrorResumeNext(e -> {
                                    return Maybe.just(0.0);
                                })
                                .map(temp -> {
                                    CityMenu item = new CityMenu();
                                    item.setSelected(city.isSelected());
                                    item.setName(city.getName());
                                    item.setDescription(city.getDescription());
                                    item.setCityId(city.getCityId());
                                    item.setTemp(temp);
                                    return item;
                                })
                                .subscribe(cityMenu -> cityMenus.add(cityMenu));


                    }

                    return cityMenus;
                })
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(cities -> {
                    getViewState().showCities(cities);
                });
    }

    public void deleteCityById(CityMenu cityMenu) {
        changeCityInteractor.deleteCityById(cityMenu.getCityId());
    }

    void addNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    void showWeatherFragment() {
        navigationManager.navigateTo(WeatherFragment.newInstance());
    }

    void showChangeCityFragment() {
        navigationManager.navigateToAndAddBackStack(ChangeCityFragment.newInstance());
    }
}
