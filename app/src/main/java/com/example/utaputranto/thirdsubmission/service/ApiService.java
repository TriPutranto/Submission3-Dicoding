package com.example.utaputranto.thirdsubmission.service;

import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String API_KEY = "?api_key=d59311d12369ddf84507a65a252aad62";

    @GET("movie/now_playing/" + API_KEY)
    Call<MovieResponse> getNowPlaying();

    @GET("movie/{movie_id}" + API_KEY)
    Call<Movie>getDetail(@Path("movie_id") String movie_id);

    @GET("tv/popular/" + API_KEY)
    Call<MovieResponse> getTvPopular();

    @GET("tv/{tv_id}" + API_KEY)
    Call<Movie>getDetailTv(@Path("tv_id") String movie_id);

}
