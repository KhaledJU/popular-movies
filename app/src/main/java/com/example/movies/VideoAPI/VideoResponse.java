package com.example.movies.VideoAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("results")
    private List<Video> videoList;

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }
}
