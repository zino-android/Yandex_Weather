package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CitySuggestion {
    @SerializedName("predictions")
    private List<Prediction> predictions;
    @SerializedName("status")
    private String status;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public String getStatus() {
        return status;
    }
}
