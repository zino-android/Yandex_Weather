package com.chichkanov.yandex_weather.utils;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.chichkanov.yandex_weather.App;

import javax.inject.Singleton;

@Singleton
public class Settings {

    private final static SharedPreferences prefsDefault = PreferenceManager.getDefaultSharedPreferences(App.getContext());

    public static long getAutoRefreshTime() {
        String updateValue = prefsDefault.getString("refresh_update", "0");
        return updateValue.equals("0") ? 0 : Long.valueOf(updateValue);
    }

    public static void saveLastUpdateTime() {
        prefsDefault.edit().putLong("last_update", System.currentTimeMillis()).apply();
    }

    public static long getLastUpdateTime() {
        return prefsDefault.getLong("last_update", 0);
    }
}
