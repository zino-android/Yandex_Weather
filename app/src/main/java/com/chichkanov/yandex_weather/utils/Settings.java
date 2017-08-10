package com.chichkanov.yandex_weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.chichkanov.yandex_weather.App;

import javax.inject.Singleton;

@Singleton
public class Settings {

    public final static String ABOUT_KEY = "about";
    private final static String REFRESH_UPDATE_KEY = "refresh_update";
    private final static String LAST_UPDATE_KEY = "last_update";
    private static SharedPreferences prefsDefault;
    private Context context;

    public Settings(Context context) {
        App.getComponent().inject(this);
        prefsDefault = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }


    public long getAutoRefreshTime() {
        String updateValue = prefsDefault.getString(REFRESH_UPDATE_KEY, "0");
        return updateValue.equals("0") ? 0 : Long.valueOf(updateValue);
    }

    public void saveLastUpdateTime() {
        prefsDefault.edit().putLong(LAST_UPDATE_KEY, System.currentTimeMillis()).apply();
    }

    public long getLastUpdateTime() {
        return prefsDefault.getLong(LAST_UPDATE_KEY, 0);
    }
}
