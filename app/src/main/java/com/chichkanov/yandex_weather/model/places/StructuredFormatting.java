package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StructuredFormatting {
    @SerializedName("main_text")
    private String mainText;
    @SerializedName("main_text_matched_substrings")
    private List<MainTextMatchedSubstring> mainTextMatchedSubstrings;
    @SerializedName("secondary_text")
    private String secondaryText;

    public String getMainText() {
        return mainText;
    }

    public List<MainTextMatchedSubstring> getMainTextMatchedSubstrings() {
        return mainTextMatchedSubstrings;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
