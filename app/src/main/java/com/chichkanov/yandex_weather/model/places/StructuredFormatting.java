package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;


public class StructuredFormatting {
    @SerializedName("main_text")
    private String mainText;
    @SerializedName("secondary_text")
    private String secondaryText;

    public String getMainText() {
        return mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
