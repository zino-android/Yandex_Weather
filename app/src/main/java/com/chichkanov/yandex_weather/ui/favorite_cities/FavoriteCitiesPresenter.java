package com.chichkanov.yandex_weather.ui.favorite_cities;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractor;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
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
                .flatMapSingle(list -> Observable.fromIterable(list)
                        .flatMapMaybe(city -> weatherInteractor
                                .getCurrentWeatherFromDBbyId(city.getCityId())
                                .onErrorResumeNext(e -> {
                                    return Maybe.just(Double.POSITIVE_INFINITY);
                                })
                                .map(temp -> {
                                    CityMenu item = new CityMenu();
                                    item.setSelected(city.isSelected());
                                    item.setName(city.getName());
                                    item.setDescription(city.getDescription());
                                    item.setCityId(city.getCityId());
                                    item.setTemp(temp);
                                    return item;
                                }))
                        .toList())
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
