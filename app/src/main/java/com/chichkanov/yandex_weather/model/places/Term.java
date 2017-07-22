package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;


public class Term {
    @SerializedName("offset")
    private int offset;
    @SerializedName("value")
    private String value;

    public int getOffset() {
        return offset;
    }

    public String getValue() {
        return value;
    }
}
