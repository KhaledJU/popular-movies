package com.example.movies.MovieAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SingleMovieService {
    @GET("3/movie/{movie_id}")
    Call<Movie> get(
            @Path("movie_id") int movie_id,
            @Query("api_key") String apikey
    );
}
