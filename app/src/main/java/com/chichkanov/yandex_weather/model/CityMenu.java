package com.chichkanov.yandex_weather.model;


public class CityMenu {

    private int id;
    private int cityId;
    private String name;
    private String description;
    private boolean isSelected;

    private double temp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityMenu cityMenu = (CityMenu) o;

        if (id != cityMenu.id) return false;
        if (cityId != cityMenu.cityId) return false;
        if (isSelected != cityMenu.isSelected) return false;
        if (Double.compare(cityMenu.temp, temp) != 0) return false;
        if (!name.equals(cityMenu.name)) return false;
        return description.equals(cityMenu.description);

    }

    @Override
    public int hashCode() {
        int result;
        long temp1;
        result = id;
        result = 31 * result + cityId;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        temp1 = Double.doubleToLongBits(temp);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        return result;
    }
}
