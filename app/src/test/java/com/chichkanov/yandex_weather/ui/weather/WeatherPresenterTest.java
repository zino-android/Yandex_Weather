package com.chichkanov.yandex_weather.ui.weather;


import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.settings.SettingsFragment;
import com.chichkanov.yandex_weather.utils.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {
    @Mock
    WeatherInteractorImpl weatherInteractor;

    @Mock
    ChangeCityInteractor changeCityInteractor;

    @Mock
    WeatherView weatherView;

    @Mock
    Settings settings;

    private CurrentWeather currentWeather;
    private City city;

    private NavigationManager navigationManager;

    private TestScheduler testScheduler = new TestScheduler();

    private Forecast forecast;


    @InjectMocks
    WeatherPresenter presenter = new WeatherPresenter(weatherInteractor, changeCityInteractor, settings, testScheduler, testScheduler);

    String json = "{\"coord\":{\"lon\":37.62,\"lat\":55.75},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":20,\"pressure\":1004,\"humidity\":94,\"temp_min\":20,\"temp_max\":20},\"visibility\":10000,\"wind\":{\"speed\":2,\"deg\":60},\"clouds\":{\"all\":0},\"dt\":1501383600,\"sys\":{\"type\":1,\"id\":7323,\"message\":0.0027,\"country\":\"RU\",\"sunrise\":1501378279,\"sunset\":1501436359},\"id\":524901,\"name\":\"Moscow\",\"cod\":200}";

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

        currentWeather = new CurrentWeather();
        currentWeather.setMaxTemp(25);
        currentWeather.setPressure(1012);
        currentWeather.setClouds(72);
        currentWeather.setCityId(354);
        currentWeather.setSunset(1485794875);
        currentWeather.setSunrise(1485762037);
        currentWeather.setDateTime(1485789600);
        currentWeather.setDescription("light intensity drizzle");
        currentWeather.setTitle("Drizzle");
        currentWeather.setIcon("01d");
        currentWeather.setHumidity(81);
        currentWeather.setWindDegree(0);
        currentWeather.setWindSpeed(4.1);
        currentWeather.setMaxTemp(29);
        currentWeather.setMinTemp(20);

        when(weatherInteractor.getWeather()).thenReturn(Flowable.just(currentWeather));
        when(weatherInteractor.getForecasts()).thenReturn(Single.just(new ArrayList<>()));
        when(changeCityInteractor.getCurrentCity()).thenReturn(Flowable.just(city));

        presenter.attachView(weatherView);

        forecast = new Forecast();
        forecast.setId(1);
        forecast.setCityId(524901);
        forecast.setWindDegree(71);
        forecast.setWindSpeed(2);
        forecast.setRain(0);
        forecast.setTitle("Clouds");
        forecast.setDescription("few clouds");
        forecast.setIcon("02d");
        forecast.setDateTime(1502787600);
        forecast.setDayTemp(29);
        forecast.setNightTemp(20);
        forecast.setEveningTemp(23);
        forecast.setMorningTemp(23);
        forecast.setMinTemp(1);
        forecast.setMaxTemp(30);

    }

    @Test
    public void testLoadCurrentWeather() {
        when(weatherInteractor.getWeather()).thenReturn(Flowable.just(currentWeather));

        presenter.loadCurrentWeather();
        verify(weatherView, times(2)).showLoading();
        testScheduler.triggerActions();
        verify(weatherView, times(3)).hideLoading();
        verify(weatherView, times(2)).showWeather(any(), any());
    }

    @Test
    public void testLoadCurrentWeatherError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(weatherInteractor.getWeather()).thenReturn(Flowable.error(nothing));

        presenter.loadCurrentWeather();
        verify(weatherView, times(2)).showLoading();
        testScheduler.triggerActions();
        verify(weatherView, times(3)).hideLoading();
        verify(weatherView, times(1)).showError();
    }

    @Test
    public void testOnRefresh() {
        when(weatherInteractor.getCurrentWeatherFromInternet()).thenReturn(Single.just(currentWeather));
        when(weatherInteractor.getForecastsFromInternet()).thenReturn(Single.just(new ArrayList<>()));

        presenter.onRefresh();
        verify(weatherInteractor).getCurrentWeatherFromInternet();
        verify(weatherInteractor).getForecastsFromInternet();
    }

    @Test
    public void testOnMenuSettingsClicked() {
        doThrow().doNothing().when(navigationManager).navigateToAndAddBackStack(any());
        presenter.addNavigationManager(navigationManager);
        presenter.onMenuSettingsClick();
        verify(navigationManager).navigateToAndAddBackStack(any(SettingsFragment.class));

    }

    @Test
    public void testLoadForecastFromInternetSuccess() {
        List<Forecast> forecastList = new ArrayList<>();
        forecastList.add(forecast);

        when(weatherInteractor.getForecastsFromInternet()).thenReturn(Single.just(forecastList));

        presenter.loadForecastFromInternet();

        testScheduler.triggerActions();
        verify(weatherView, times(3)).hideLoading();
        verify(weatherView).showForecast(forecastList);

    }

    @Test
    public void testLoadForecastFromInternetError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(weatherInteractor.getForecastsFromInternet()).thenReturn(Single.error(nothing));

        presenter.loadForecastFromInternet();

        testScheduler.triggerActions();
        verify(weatherView, times(4)).hideLoading();
        verify(weatherView).showError();

    }

    @Test
    public void testLoadCurrentWeatherFromInternetSuccess() {
        when(weatherInteractor.getCurrentWeatherFromInternet()).thenReturn(Single.just(currentWeather));

        presenter.loadWeatherFromInternet();
        testScheduler.triggerActions();
        verify(weatherView, times(3)).hideLoading();
        verify(weatherView, times(2)).showWeather(any(), any());
    }

    @Test
    public void testLoadCurrentWeatherFromInternetError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(weatherInteractor.getCurrentWeatherFromInternet()).thenReturn(Single.error(nothing));
        when(weatherInteractor.getWeather()).thenReturn(Flowable.error(nothing));
        presenter.loadWeatherFromInternet();
        testScheduler.triggerActions();
        verify(weatherView, times(4)).hideLoading();
        verify(weatherView, times(2)).showError();
    }


    @Test
    public void testLoadForecastWeatherError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(weatherInteractor.getForecasts()).thenReturn(Single.error(nothing));
        presenter.loadForecastWeather();
        testScheduler.triggerActions();
        verify(weatherView, times(3)).hideLoading();
        verify(weatherView).showError();
    }

    @Test
    public void testAddNullNavigationManagerAndCallOnMenuChangeCityClick() {
        presenter.attachView(weatherView);
        presenter.addNavigationManager(null);
        presenter.onMenuChangeCityClick();
        verifyZeroInteractions(navigationManager);
    }

    @Test
    public void testOnMenuChangeCityClick() {
        presenter.addNavigationManager(navigationManager);
        presenter.onMenuChangeCityClick();
        verify(navigationManager).navigateToAndAddBackStack(any());
        verifyNoMoreInteractions(navigationManager);

    }


}