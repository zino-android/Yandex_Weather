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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoriteCitiesPresenter extends MvpPresenter<FavoriteCitiesView> {

    private ChangeCityInteractor changeCityInteractor;

    private WeatherInteractor weatherInteractor;

    private NavigationManager navigationManager;

    @Inject
    public FavoriteCitiesPresenter(ChangeCityInteractor changeCityInteractor, WeatherInteractor weatherInteractor) {
        this.changeCityInteractor = changeCityInteractor;
        this.weatherInteractor = weatherInteractor;
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

                        double temp = weatherInteractor
                                .getCurrentWeatherFromDBbyId(city.getCityId())
                                .subscribeOn(Schedulers.io())
                                .onErrorResumeNext(e -> {
                                    return Maybe.just(0.0);
                                })
                                .blockingGet();

                        CityMenu item = new CityMenu();
                        item.setSelected(city.isSelected());
                        item.setName(city.getName());
                        item.setDescription(city.getDescription());
                        item.setCityId(city.getCityId());
                        item.setTemp(temp);
                        cityMenus.add(item);
                    }

                    return cityMenus;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
