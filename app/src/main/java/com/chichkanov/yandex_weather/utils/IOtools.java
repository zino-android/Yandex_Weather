package com.chichkanov.yandex_weather.utils;

import android.content.Context;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Singleton;

@Singleton
public class IOtools {

    private static final String CURRENT_WEATHER_SAVE_NAME = "current_weather.txt";

    public static void saveCurrentWeather(CurrentWeather currentWeather) {
        Gson gson = new Gson();
        String s = gson.toJson(currentWeather);
        FileOutputStream outputStream;
        try {
            outputStream = App.getContext().openFileOutput(CURRENT_WEATHER_SAVE_NAME, Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CurrentWeather getCurrentWeather() {
        FileInputStream fis = null;
        try {
            fis = App.getContext().openFileInput(CURRENT_WEATHER_SAVE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis != null && System.currentTimeMillis() - Settings.getLastUpdateTime() > Settings.getAutoRefreshTime()) {
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String json = sb.toString();
            Gson gson = new Gson();
            return gson.fromJson(json, CurrentWeather.class);
        } else return null;
    }

}
