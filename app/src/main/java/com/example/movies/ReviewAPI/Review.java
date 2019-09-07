package com.example.movies.ReviewAPI;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
