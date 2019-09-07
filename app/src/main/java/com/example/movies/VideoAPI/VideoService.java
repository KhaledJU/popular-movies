package com.example.movies.VideoAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VideoService {
    @GET("3/movie/{movie_id}/videos")
    Call<VideoResponse> get(
            @Path("movie_id") int movie_id,
            @Query("api_key") String apikey
    );
}
