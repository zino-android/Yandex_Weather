package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.db.CityDao;
import com.chichkanov.yandex_weather.db.CurrentWeatherDao;
import com.chichkanov.yandex_weather.db.ForecastDao;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeatherResponse;
import com.chichkanov.yandex_weather.model.current_weather.Weather;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;
import com.chichkanov.yandex_weather.utils.Settings;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherRepositoryImplTest {

    private TestScheduler scheduler;

    @Mock
    private WeatherApi weatherApi;
    @Mock
    private Settings settings;
    @Mock
    private WeatherDatabase database;

    @Mock
    private CityDao cityDao;
    @Mock
    private CurrentWeatherDao currentWeatherDao;
    @Mock
    private ForecastDao forecastDao;

    private City city;

    @InjectMocks
    private WeatherRepositoryImpl repository;

    private CurrentWeather currentWeather;

    private String json = "{\"coord\":{\"lon\":37.62,\"lat\":55.75},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":20,\"pressure\":1004,\"humidity\":94,\"temp_min\":20,\"temp_max\":20},\"visibility\":10000,\"wind\":{\"speed\":2,\"deg\":60},\"clouds\":{\"all\":0},\"dt\":1501383600,\"sys\":{\"type\":1,\"id\":7323,\"message\":0.0027,\"country\":\"RU\",\"sunrise\":1501378279,\"sunset\":1501436359},\"id\":524901,\"name\":\"Moscow\",\"cod\":200}";
    private String forecastJsonResponse = "{\"city\":{\"id\":524901,\"name\":\"Moscow\",\"coord\":{\"lon\":37.6156,\"lat\":55.7522},\"country\":\"RU\",\"population\":0},\"cod\":\"200\",\"message\":0.1239805,\"cnt\":7,\"list\":[{\"dt\":1502528400,\"temp\":{\"day\":289.48,\"min\":286.91,\"max\":289.48,\"night\":286.91,\"eve\":289.48,\"morn\":289.48},\"pressure\":1010.9,\"humidity\":80,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01n\"}],\"speed\":0.77,\"deg\":71,\"clouds\":0},{\"dt\":1502614800,\"temp\":{\"day\":297.9,\"min\":286.55,\"max\":299.58,\"night\":294.87,\"eve\":299.41,\"morn\":286.55},\"pressure\":1009.7,\"humidity\":67,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":4.36,\"deg\":161,\"clouds\":0},{\"dt\":1502701200,\"temp\":{\"day\":293.97,\"min\":289.56,\"max\":295.38,\"night\":289.56,\"eve\":293.78,\"morn\":292.46},\"pressure\":1009.22,\"humidity\":100,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":3.21,\"deg\":333,\"clouds\":56,\"rain\":6.75},{\"dt\":1502787600,\"temp\":{\"day\":293.71,\"min\":285.76,\"max\":293.78,\"night\":285.76,\"eve\":293.53,\"morn\":286.2},\"pressure\":1020,\"humidity\":75,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"speed\":3.57,\"deg\":15,\"clouds\":24},{\"dt\":1502874000,\"temp\":{\"day\":296.02,\"min\":285.35,\"max\":296.02,\"night\":285.35,\"eve\":290.46,\"morn\":291.23},\"pressure\":1023.98,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":5.06,\"deg\":66,\"clouds\":7},{\"dt\":1502960400,\"temp\":{\"day\":297.6,\"min\":288.36,\"max\":297.6,\"night\":288.36,\"eve\":292.27,\"morn\":291.73},\"pressure\":1022.96,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":3.99,\"deg\":125,\"clouds\":0},{\"dt\":1503046800,\"temp\":{\"day\":299.16,\"min\":287.17,\"max\":299.16,\"night\":287.17,\"eve\":292.22,\"morn\":293.14},\"pressure\":1021.14,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":2.6,\"deg\":201,\"clouds\":0}]}";
    private CurrentWeatherResponse currentWeatherResponse;
    private ForecastResponse forecastResponse;

    private Forecast forecast;


    @Before
    public void setUp() throws Exception {
        scheduler = new TestScheduler();

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

        currentWeatherResponse = new Gson().fromJson(json, CurrentWeatherResponse.class);

        when(database.cityDao()).thenReturn(cityDao);
        when(cityDao.loadCurrentCity()).thenReturn(Maybe.just(city));
        when(database.currentWeatherDao()).thenReturn(currentWeatherDao);

        forecastResponse = new Gson().fromJson(forecastJsonResponse, ForecastResponse.class);
        when(database.forecastDao()).thenReturn(forecastDao);

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
    public void getWeather() throws Exception {

        when(database.cityDao()).thenReturn(cityDao);
        when(cityDao.loadCurrentCity()).thenReturn(Maybe.just(city));
        when(database.currentWeatherDao()).thenReturn(currentWeatherDao);
        when(currentWeatherDao.loadCurrentWeatherByCityId(city.getCityId()))
                .thenReturn(Single.just(currentWeather));

        TestSubscriber<CurrentWeather> subscriber = repository.getWeather().test();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);

        scheduler.triggerActions();
    }


    @Test
    public void testGetWeatherError() throws Exception {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(database.cityDao()).thenReturn(cityDao);
        when(cityDao.loadCurrentCity()).thenReturn(Maybe.just(city));
        when(database.currentWeatherDao()).thenReturn(currentWeatherDao);
        when(currentWeatherDao.loadCurrentWeatherByCityId(city.getCityId()))
                .thenReturn(Single.error(nothing));
        when(weatherApi.getWeather(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(currentWeatherResponse));

        TestSubscriber<CurrentWeather> subscriber = repository.getWeather().test();
        subscriber.assertValueCount(1);

        scheduler.triggerActions();
        verify(cityDao).updateSelectedCityId(anyInt());
        verify(settings).saveLastUpdateTime();
        verify(currentWeatherDao).insertCurrentWeather(any(CurrentWeather.class));
    }

    @Test
    public void testGetCurrentWeatherFromInternetSuccess() {
        when(weatherApi.getWeather(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(currentWeatherResponse));

        TestObserver<CurrentWeather> observer = repository.getCurrentWeatherFromInternet().test();
        verify(cityDao).updateSelectedCityId(anyInt());
        verify(settings).saveLastUpdateTime();
        verify(currentWeatherDao).insertCurrentWeather(any(CurrentWeather.class));
    }

    @Test
    public void testGetCurrentWeatherFromInternetError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(weatherApi.getWeather(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.error(nothing));

        TestObserver<CurrentWeather> observer = repository.getCurrentWeatherFromInternet().test();

        verify(cityDao, never()).updateSelectedCityId(anyInt());
        verify(settings, never()).saveLastUpdateTime();
        verify(currentWeatherDao, never()).insertCurrentWeather(any(CurrentWeather.class));
    }


    @Test
    public void testGetCurrentTempbyDb() {
        when(currentWeatherDao.loadCurrentTempByCityId(anyInt())).thenReturn(Single.just(25.0));

        TestObserver observer = repository.getCurrentTempFromDBbyCityId(2).test();
        observer.assertNoErrors();
        observer.assertValue(25.0);

    }

    @Test
    public void testGetForecastFromInternetSuccess() {
        when(weatherApi.getForecasts(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(forecastResponse));

        TestObserver observer = repository.getForecastFromInternet().test();

        scheduler.triggerActions();
        observer.assertNoErrors();
        observer.assertValueCount(1);

        verify(forecastDao).insertForecasts(anyList());
        verify(forecastDao).deleteOldForecasts(anyLong());
    }

    @Test
    public void testGetForecastFromInternetError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(weatherApi.getForecasts(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.error(nothing));

        TestObserver observer = repository.getForecastFromInternet().test();

        scheduler.triggerActions();
        observer.assertError(nothing);
        observer.assertValueCount(0);

        verify(forecastDao, never()).insertForecasts(anyList());
        verify(forecastDao, never()).deleteOldForecasts(anyLong());
    }

    @Test
    public void testGetForecast() throws Exception {

        when(database.cityDao()).thenReturn(cityDao);
        when(cityDao.loadCurrentCity()).thenReturn(Maybe.just(city));
        when(database.forecastDao()).thenReturn(forecastDao);
        ArrayList<Forecast> forecasts = new ArrayList<>();
        forecasts.add(forecast);
        when(forecastDao.loadForecastByDateTimeAndCityId(anyLong(), anyInt()))
                .thenReturn(Single.just(forecasts));

        TestObserver<List<Forecast>> subscriber = repository.getForecasts().test();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

    @Test
    public void testGetForecastError() throws Exception {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));

        when(database.cityDao()).thenReturn(cityDao);
        when(cityDao.loadCurrentCity()).thenReturn(Maybe.just(city));
        when(database.forecastDao()).thenReturn(forecastDao);
        when(forecastDao.loadForecastByDateTimeAndCityId(anyLong(), anyInt()))
                .thenReturn(Single.error(nothing));
        when(weatherApi.getForecasts(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(forecastResponse));

        TestObserver<List<Forecast>> subscriber = repository.getForecasts().test();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);

        scheduler.triggerActions();
        verify(forecastDao, times(1)).insertForecasts(anyList());
        verify(forecastDao, times(1)).deleteOldForecasts(anyLong());
    }

    @Test
    public void testForecastEqualsAndHashCode() {
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

        Forecast forecast2 = new Forecast();
        forecast2.setId(1);
        forecast2.setCityId(524901);
        forecast2.setWindDegree(71);
        forecast2.setWindSpeed(2);
        forecast2.setRain(0);
        forecast2.setTitle("Clouds");
        forecast2.setDescription("few clouds");
        forecast2.setIcon("02d");
        forecast2.setDateTime(1502787600);
        forecast2.setDayTemp(29);
        forecast2.setNightTemp(20);
        forecast2.setEveningTemp(23);
        forecast2.setMorningTemp(23);
        forecast2.setMinTemp(1);
        forecast2.setMaxTemp(30);

        assertTrue(forecast.hashCode() == forecast2.hashCode());

        assertTrue(forecast.equals(forecast2));
        assertTrue(forecast2.equals(forecast));
        assertTrue(forecast.equals(forecast));
        assertFalse(forecast.equals(null));

        forecast2.setDateTime(8488348);

        assertFalse(forecast2.hashCode() == forecast.hashCode());
        assertFalse(forecast2.equals(forecast));
    }


    @Test
    public void testCurrentWeatherEqualsAndHashCode() {
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

        CurrentWeather currentWeather2 = new CurrentWeather();
        currentWeather2.setMaxTemp(25);
        currentWeather2.setPressure(1012);
        currentWeather2.setClouds(72);
        currentWeather2.setCityId(354);
        currentWeather2.setSunset(1485794875);
        currentWeather2.setSunrise(1485762037);
        currentWeather2.setDateTime(1485789600);
        currentWeather2.setDescription("light intensity drizzle");
        currentWeather2.setTitle("Drizzle");
        currentWeather2.setIcon("01d");
        currentWeather2.setHumidity(81);
        currentWeather2.setWindDegree(0);
        currentWeather2.setWindSpeed(4.1);
        currentWeather2.setMaxTemp(29);
        currentWeather2.setMinTemp(20);

        assertTrue(currentWeather.hashCode() == currentWeather2.hashCode());

        assertTrue(currentWeather.equals(currentWeather2));
        assertTrue(currentWeather2.equals(currentWeather));
        assertTrue(currentWeather.equals(currentWeather));
        assertFalse(currentWeather.equals(null));

        currentWeather2.setDateTime(8488348);

        assertFalse(currentWeather2.hashCode() == currentWeather.hashCode());
        assertFalse(currentWeather2.equals(currentWeather));
    }

}