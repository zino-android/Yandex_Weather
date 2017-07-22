package com.chichkanov.yandex_weather.model.places;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Prediction {
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private String id;
    @SerializedName("matched_substrings")
    private List<MatchedSubstring> matchedSubstrings;
    @SerializedName("place_id")
    private String placeId;
    @SerializedName("reference")
    private String reference;
    @SerializedName("structured_formatting")
    private StructuredFormatting structuredFormatting;
    @SerializedName("terms")
    private List<Term> terms;
    @SerializedName("types")
    private List<String> types;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public List<MatchedSubstring> getMatchedSubstrings() {
        return matchedSubstrings;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getReference() {
        return reference;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public List<String> getTypes() {
        return types;
    }
}
