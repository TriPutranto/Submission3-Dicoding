package com.example.utaputranto.thirdsubmission.service;

import com.example.utaputranto.thirdsubmission.BuildConfig;
import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.model.MovieResponse;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import com.example.utaputranto.thirdsubmission.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String API_KEY = "?api_key=d59311d12369ddf84507a65a252aad62";
    @GET("movie/now_playing/" +  BuildConfig.ApiKey)
    Call<MovieResponse> getNowPlaying();

    @GET("movie/{movie_id}" + BuildConfig.ApiKey)
    Call<Movie>getDetail(@Path("movie_id") String movie_id);

    @GET("tv/popular/" + BuildConfig.ApiKey)
    Call<TvShowResponse> getTvPopular();

    @GET("tv/{tv_id}" + BuildConfig.ApiKey)
    Call<TvShow>getDetailTv(@Path("tv_id") String tv_id);

}
