package com.chichkanov.yandex_weather.di.modules;

import android.content.Context;

import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(i -> {
                    if (!NetworkUtils.isConnected(context)) {
                        throw new IOException();
                    }
                    return i.proceed(i.request());
                })
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build();
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

}
