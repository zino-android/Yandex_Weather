package com.chichkanov.yandex_weather.ui.favorite_cities;

import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteCitiesPresenterTest {

    @Mock
    ChangeCityInteractor changeCityInteractor;

    @Mock
    FavoriteCitiesView favoriteCitiesView;

    @Mock
    WeatherInteractorImpl weatherInteractor;

    private NavigationManager navigationManager;

    private TestScheduler testScheduler = new TestScheduler();

    private City city;


    @InjectMocks
    FavoriteCitiesPresenter presenter = new FavoriteCitiesPresenter(changeCityInteractor, weatherInteractor,
            testScheduler,
            testScheduler);

    @Before
    public void setUp() {

        navigationManager = Mockito.mock(NavigationManager.class);

        city = new City();
        city.setName("Moscow");
        city.setSelected(true);
        city.setDescription("RU");
        city.setPlacesId("sggfs43t");
        city.setId(1);
        city.setCityId(346634);


        presenter.attachView(favoriteCitiesView);
        presenter.addNavigationManager(navigationManager);
    }



    @Test
    public void onCitySelectedChanged() throws Exception {
        doThrow().doNothing().when(changeCityInteractor).setCitySelected(777);

        presenter.onCitySelectedChanged(777);

        verify(navigationManager).navigateTo(any(WeatherFragment.class));
    }

/*
    @Test
    public void testLoadCities() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(city);
        when(changeCityInteractor.getCities()).thenReturn(Flowable.just(cities));
        when(weatherInteractor.getCurrentWeatherFromDBbyId(city.getCityId()))
                .thenReturn(Maybe.just(5.0));

        ArrayList<CityMenu> cityMenus = new ArrayList<CityMenu>();
        CityMenu item = new CityMenu();
        item.setSelected(city.isSelected());
        item.setName(city.getName());
        item.setDescription(city.getDescription());
        item.setCityId(city.getCityId());
        item.setTemp(5.0);
        cityMenus.add(item);

        presenter.loadCities();


        testScheduler.triggerActions();

        verify(favoriteCitiesView).showCities(any());
    }
    */

/*
    @Test
    public void testLoadCitiesError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        ArrayList<City> cities = new ArrayList<>();
        cities.add(city);
        when(changeCityInteractor.getCities()).thenReturn(Flowable.just(cities));
        when(weatherInteractor.getCurrentWeatherFromDBbyId(city.getCityId()))
                .thenReturn(Maybe.error(nothing));

        ArrayList<CityMenu> cityMenus = new ArrayList<CityMenu>();
        CityMenu item = new CityMenu();
        item.setSelected(city.isSelected());
        item.setName(city.getName());
        item.setDescription(city.getDescription());
        item.setCityId(city.getCityId());
        item.setTemp(0.0);
        cityMenus.add(item);

        presenter.loadCities();


        testScheduler.triggerActions();

        verify(favoriteCitiesView).showCities(any());
    }
*/
    @Test
    public void testShowCityFragment() {
        presenter.showChangeCityFragment();
        verify(navigationManager).navigateToAndAddBackStack(any(ChangeCityFragment.class));
    }

    @Test
    public void testDeleteCityById() {
        doThrow().doNothing().when(changeCityInteractor).deleteCityById(city.getCityId());

        CityMenu item = new CityMenu();
        item.setSelected(city.isSelected());
        item.setName(city.getName());
        item.setDescription(city.getDescription());
        item.setCityId(city.getCityId());
        item.setTemp(5.0);

        presenter.deleteCityById(item);
        verify(changeCityInteractor).deleteCityById(city.getCityId());
    }

}