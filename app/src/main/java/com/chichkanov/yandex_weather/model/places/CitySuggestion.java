package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitySuggestion {
    @SerializedName("predictions")
    private List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }
}
