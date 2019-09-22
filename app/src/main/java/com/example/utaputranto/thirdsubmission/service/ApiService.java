package com.example.utaputranto.thirdsubmission.service;

import com.example.utaputranto.thirdsubmission.BuildConfig;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;
import com.example.utaputranto.thirdsubmission.model.movie.MovieResponse;
import com.example.utaputranto.thirdsubmission.model.tvshow.TvShow;
import com.example.utaputranto.thirdsubmission.model.tvshow.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/movie" + BuildConfig.ApiKey)
    Call<MovieResponse>
    getSearchMovie(
            @Query("query") String query
    );

    @GET("discover/movie" + BuildConfig.ApiKey)
    Call<MovieResponse>
    getReleaseToday(
            @Query("primary_release_date.gte") String gteDate,
            @Query("primary_release_date.lte") String lteDate
    );

    @GET("search/tv" + BuildConfig.ApiKey)
    Call<TvShowResponse>
    getSearchTv(
            @Query("query") String query
    );

    @GET("movie/now_playing/" + BuildConfig.ApiKey)
    Call<MovieResponse>
    getNowPlaying();

    @GET("movie/{movie_id}" + BuildConfig.ApiKey)
    Call<Movie>
    getDetail(
            @Path("movie_id") String movie_id
    );

    @GET("tv/popular/" + BuildConfig.ApiKey)
    Call<TvShowResponse>
    getTvPopular();

    @GET("tv/{tv_id}" + BuildConfig.ApiKey)
    Call<TvShow>
    getDetailTv(
            @Path("tv_id") String tv_id
    );
}
