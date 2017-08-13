package com.chichkanov.yandex_weather.ui.adapter;

import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.ui.main.CityMenuDiffUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ForecastItemDiffCallbackTest {

    private Forecast forecast;
    ArrayList<Forecast> oldList;
    ArrayList<Forecast> newList;

    ForecastItemDiffCallback utils;

    @Before
    public void setUp() throws Exception {
        oldList = new ArrayList<>();
        newList = new ArrayList<>();

        utils = new ForecastItemDiffCallback(oldList, newList);
    }

    @Test
    public void areItemsTheSame() throws Exception {
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

        oldList.add(forecast);
        newList.add(forecast2);

        assertTrue(utils.areItemsTheSame(0, 0));

        forecast2.setDateTime(100000000);
        assertFalse(utils.areItemsTheSame(0, 0));


    }

    @Test
    public void areContentsTheSame() throws Exception {
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

        oldList.add(forecast);
        newList.add(forecast2);

        assertTrue(utils.areContentsTheSame(0, 0));

        forecast2.setDateTime(100000000);
        assertFalse(utils.areContentsTheSame(0, 0));

    }

}