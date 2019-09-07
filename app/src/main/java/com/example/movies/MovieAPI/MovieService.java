package com.example.movies.MovieAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("3/movie/{category}")
    Call<MovieResponse> get(
            @Path("category") String category,
            @Query("api_key") String apikey
    );
}
