package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;


class MatchedSubstring {
    @SerializedName("length")
    private int length;
    @SerializedName("offset")
    private int offset;

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset;
    }
}
