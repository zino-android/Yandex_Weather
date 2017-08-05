package com.chichkanov.yandex_weather.ui.weather;


import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;

import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    private String DEFAULT_CITY = "Moscow";

    @Mock
    WeatherInteractorImpl weatherInteractor;

    @Mock
    WeatherView weatherView;

    @Mock
    Settings settings;

    @Mock
    IOtools iOtools;


    private NavigationManager navigationManager;

    private TestScheduler testScheduler = new TestScheduler();

    @InjectMocks
    WeatherPresenter presenter = new WeatherPresenter(weatherInteractor, settings, iOtools, testScheduler, testScheduler);

    String json = "{\"coord\":{\"lon\":37.62,\"lat\":55.75},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":20,\"pressure\":1004,\"humidity\":94,\"temp_min\":20,\"temp_max\":20},\"visibility\":10000,\"wind\":{\"speed\":2,\"deg\":60},\"clouds\":{\"all\":0},\"dt\":1501383600,\"sys\":{\"type\":1,\"id\":7323,\"message\":0.0027,\"country\":\"RU\",\"sunrise\":1501378279,\"sunset\":1501436359},\"id\":524901,\"name\":\"Moscow\",\"cod\":200}";

    @Before
    public void setUp() {
        navigationManager = Mockito.mock(NavigationManager.class);
        presenter.attachView(weatherView);
    }


    @Test
    public void testLoadWeatherErrorWithoutCache() throws Exception {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(weatherInteractor.getWeather(DEFAULT_CITY)).thenReturn(Observable.error(nothing));
        when(settings.getCurrentCity()).thenReturn(DEFAULT_CITY);

        presenter.loadCurrentWeather();
        verify(weatherView, times(1)).showLoading();
        testScheduler.triggerActions();
        verify(weatherView, times(1)).hideLoading();
        verify(weatherView, times(1)).showError();
        verifyNoMoreInteractions(weatherView);
    }

    @Test
    public void testLoadWeatherErrorWithCache() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(weatherInteractor.getWeather(DEFAULT_CITY)).thenReturn(Observable.error(nothing));
        when(settings.getCurrentCity()).thenReturn(DEFAULT_CITY);
        when(iOtools.getCurrentWeather()).thenReturn(new Gson().fromJson(json, CurrentWeather.class));

        presenter.loadCurrentWeather();
        verify(weatherView, times(1)).showLoading();
        testScheduler.triggerActions();
        verify(weatherView, times(1)).hideLoading();
        verify(weatherView, times(0)).showError();
        verifyNoMoreInteractions(weatherView);
    }

    @Test
    public void testLoadWeatherSuccess() {
        CurrentWeather weather = new Gson().fromJson(json, CurrentWeather.class);
        when(weatherInteractor.getWeather(DEFAULT_CITY)).thenReturn(Observable.just(weather));
        when(settings.getCurrentCity()).thenReturn(DEFAULT_CITY);
        presenter.loadCurrentWeather();
        verify(weatherView, times(1)).showLoading();
        testScheduler.triggerActions();
        verify(weatherView, times(1)).hideLoading();
        verify(weatherView, times(1)).showWeather(eq(weather), anyString());
        verify(weatherView, times(1)).showCityName(DEFAULT_CITY);
        verifyNoMoreInteractions(weatherView);
    }

    @Test
    public void testDetachViewWhenSubscriptionNull() {
        presenter.detachView(weatherView);
        verifyNoMoreInteractions(weatherView);
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
        verify(navigationManager, times(1)).navigateTo(any());
        verifyNoMoreInteractions(navigationManager);

    }

}