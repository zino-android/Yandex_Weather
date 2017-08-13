package com.chichkanov.yandex_weather.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "current_weather", indices = @Index(value = "cityId", unique = true))
public class CurrentWeather {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int cityId;
    private long dateTime;
    private long sunrise;
    private long sunset;
    private double pressure;
    private int humidity;
    private double minTemp;
    private double maxTemp;
    private double temp;
    private String description;
    private String icon;
    private String title;
    private double windSpeed;
    private double windDegree;
    private int clouds;

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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentWeather that = (CurrentWeather) o;

        if (id != that.id) return false;
        if (cityId != that.cityId) return false;
        if (dateTime != that.dateTime) return false;
        if (sunrise != that.sunrise) return false;
        if (sunset != that.sunset) return false;
        if (Double.compare(that.pressure, pressure) != 0) return false;
        if (humidity != that.humidity) return false;
        if (Double.compare(that.minTemp, minTemp) != 0) return false;
        if (Double.compare(that.maxTemp, maxTemp) != 0) return false;
        if (Double.compare(that.temp, temp) != 0) return false;
        if (Double.compare(that.windSpeed, windSpeed) != 0) return false;
        if (Double.compare(that.windDegree, windDegree) != 0) return false;
        if (clouds != that.clouds) return false;
        if (!description.equals(that.description)) return false;
        if (!icon.equals(that.icon)) return false;
        return title.equals(that.title);

    }

    @Override
    public int hashCode() {
        int result;
        long temp1;
        result = id;
        result = 31 * result + cityId;
        result = 31 * result + (int) (dateTime ^ (dateTime >>> 32));
        result = 31 * result + (int) (sunrise ^ (sunrise >>> 32));
        result = 31 * result + (int) (sunset ^ (sunset >>> 32));
        temp1 = Double.doubleToLongBits(pressure);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + humidity;
        temp1 = Double.doubleToLongBits(minTemp);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(maxTemp);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(temp);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + description.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + title.hashCode();
        temp1 = Double.doubleToLongBits(windSpeed);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(windDegree);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + clouds;
        return result;
    }
}
