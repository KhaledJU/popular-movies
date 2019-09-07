package com.example.movies.ReviewAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewService {
    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewResponse> get(
            @Path("movie_id") int movie_id,
            @Query("api_key") String apikey
    );
}
