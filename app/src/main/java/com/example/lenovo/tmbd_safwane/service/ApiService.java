package com.example.lenovo.tmbd_safwane.service;

import com.example.lenovo.tmbd_safwane.models.Movie;
import com.example.lenovo.tmbd_safwane.models.Movies;
import com.example.lenovo.tmbd_safwane.models.Series;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 15/11/2017.
 */

public interface ApiService {
    @GET("movie/popular")
    Call<Movies> getPopularMovies(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<Series> getPopularSeries(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Query("api_key") String apiKey, int movie_id);
}
