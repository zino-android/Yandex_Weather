package com.chichkanov.yandex_weather.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cities", indices = {@Index(value = {"placesId"}, unique = true)})
public class City {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int cityId;
    private String placesId;
    private String name;
    private String description;
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPlacesId() {
        return placesId;
    }

    public void setPlacesId(String placesId) {
        this.placesId = placesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (cityId != city.cityId) return false;
        if (isSelected != city.isSelected) return false;
        if (!placesId.equals(city.placesId)) return false;
        if (!name.equals(city.name)) return false;
        return description.equals(city.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cityId;
        result = 31 * result + placesId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
