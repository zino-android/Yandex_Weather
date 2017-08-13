package com.chichkanov.yandex_weather.ui.main;

import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.CityMenu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CityMenuDiffUtilsTest {

    private City city;
    ArrayList<CityMenu> oldList;
    ArrayList<CityMenu> newList;

    CityMenuDiffUtils utils;

    @Before
    public void setUp() {
        city = new City();
        city.setName("Moscow");
        city.setSelected(true);
        city.setDescription("RU");
        city.setPlacesId("sggfs43t");
        city.setId(1);
        city.setCityId(346634);

        CityMenu item = new CityMenu();
        item.setSelected(city.isSelected());
        item.setName(city.getName());
        item.setDescription(city.getDescription());
        item.setCityId(city.getCityId());
        item.setTemp(5.0);

        oldList = new ArrayList<>();
        newList = new ArrayList<>();

        utils = new CityMenuDiffUtils(oldList, newList);


    }

    @Test
    public void areItemsTheSame() throws Exception {
        CityMenu item = new CityMenu();
        item.setSelected(city.isSelected());
        item.setName(city.getName());
        item.setDescription(city.getDescription());
        item.setCityId(city.getCityId());
        item.setTemp(5.0);
        CityMenu item1 = new CityMenu();
        item1.setSelected(city.isSelected());
        item1.setName(city.getName());
        item1.setDescription(city.getDescription());
        item1.setCityId(city.getCityId());
        item1.setTemp(5.0);
        oldList.add(item);
        newList.add(item1);

        assertTrue(utils.areItemsTheSame(0, 0));

        item1.setTemp(0);
        assertTrue(utils.areItemsTheSame(0, 0));

        item1.setCityId(11);
        assertFalse(utils.areItemsTheSame(0, 0));

    }

    @Test
    public void areContentsTheSame() throws Exception {
        CityMenu item = new CityMenu();
        item.setSelected(city.isSelected());
        item.setName(city.getName());
        item.setDescription(city.getDescription());
        item.setCityId(city.getCityId());
        item.setTemp(5.0);
        CityMenu item1 = new CityMenu();
        item1.setSelected(city.isSelected());
        item1.setName(city.getName());
        item1.setDescription(city.getDescription());
        item1.setCityId(city.getCityId());
        item1.setTemp(5.0);
        oldList.add(item);
        newList.add(item1);

        assertTrue(utils.areContentsTheSame(0, 0));
        item1.setTemp(100);
        assertFalse(utils.areContentsTheSame(0, 0));
    }

}