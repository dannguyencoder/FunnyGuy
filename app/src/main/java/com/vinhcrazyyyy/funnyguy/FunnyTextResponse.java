package com.vinhcrazyyyy.funnyguy;

import com.google.gson.annotations.SerializedName;

class FunnyTextResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("funny_text")
    private String funnyText;

    @SerializedName("category")
    private String category;

    public int getId() {
        return id;
    }

    public String getFunnyText() {
        return funnyText;
    }

    public String getCategory() {
        return category;
    }
}
