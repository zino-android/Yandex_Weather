package com.chichkanov.yandex_weather.di.modules;

import android.content.Context;

import com.chichkanov.yandex_weather.api.PlacesApi;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.utils.NetworkUtils;

import java.nio.channels.NoConnectionPendingException;

import javax.inject.Named;
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
                .addInterceptor(i -> {
                    if (!NetworkUtils.isConnected(context)) {
                        throw new NoConnectionPendingException();
                    }
                    return i.proceed(i.request());
                })
                .build();
    }


    @Provides
    @Singleton
    @Named("provideRetrofit")
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
    @Named("providePlacesRetrofit")
    Retrofit providePlacesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .build();
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(@Named("provideRetrofit") Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    PlacesApi providePlacesApi(@Named("providePlacesRetrofit") Retrofit retrofit) {
        return retrofit.create(PlacesApi.class);
    }

}
