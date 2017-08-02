package com.chichkanov.yandex_weather.utils;

import android.content.Context;

import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IOtools {

    @Inject
    Context context;

    public IOtools(Context context){
        this.context = context;
    }

    private static final String CURRENT_WEATHER_SAVE_NAME = "current_weather.txt";

    public void saveCurrentWeather(CurrentWeather currentWeather) {
        Gson gson = new Gson();
        String s = gson.toJson(currentWeather);
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(CURRENT_WEATHER_SAVE_NAME, Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CurrentWeather getCurrentWeather() {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(CURRENT_WEATHER_SAVE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fis != null) {
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
            try {
                fis.close();
                isr.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return gson.fromJson(json, CurrentWeather.class);
        } else return null;
    }

}
