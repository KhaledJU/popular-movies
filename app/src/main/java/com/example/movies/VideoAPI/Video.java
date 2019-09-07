package com.example.movies.VideoAPI;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
