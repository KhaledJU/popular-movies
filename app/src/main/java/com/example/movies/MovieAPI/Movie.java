package com.example.movies.MovieAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("overview")
    private String overview;

    public void setId(int id) { this.id = id; }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() { return id; }
}
