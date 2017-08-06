package com.chichkanov.yandex_weather.api;

import com.chichkanov.yandex_weather.model.current_weather.CurrentWeatherResponse;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather?")
    Single<CurrentWeatherResponse> getWeather(@Query("q") String cityName, @Query("appid") String apiKey,
                                              @Query("lang") String lang, @Query("units") String units);

    @GET("forecast/daily?")
    Single<ForecastResponse> getForecasts(@Query("q") String cityName, @Query("appid") String apiKey,
                                              @Query("lang") String lang, @Query("units") String units);
}
