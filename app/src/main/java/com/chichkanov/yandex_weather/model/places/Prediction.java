package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;


public class Prediction {
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private String id;
    @SerializedName("place_id")
    private String placeId;
    @SerializedName("structured_formatting")
    private StructuredFormatting structuredFormatting;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

}
