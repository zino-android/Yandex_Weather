package com.chichkanov.yandex_weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.chichkanov.yandex_weather.App;

import javax.inject.Singleton;

@Singleton
public class Settings {

    private static SharedPreferences prefsDefault;

    public Settings(Context context){
        App.getComponent().inject(this);
        prefsDefault = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public long getAutoRefreshTime() {
        String updateValue = prefsDefault.getString("refresh_update", "0");
        return updateValue.equals("0") ? 0 : Long.valueOf(updateValue);
    }

    public void saveLastUpdateTime() {
        prefsDefault.edit().putLong("last_update", System.currentTimeMillis()).apply();
    }

    public long getLastUpdateTime() {
        return prefsDefault.getLong("last_update", 0);
    }

    public void setCurrentCity(String city) {
        prefsDefault.edit().putString("current_city", city).apply();
    }

    public String getCurrentCity() {
        return prefsDefault.getString("current_city", "Москва");
    }
}
