package com.example.movies.ReviewAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    @SerializedName("results")
    private List<Review> reviewList;

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

}
