package com.example.utaputranto.thirdsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShow {

    @SerializedName("name")
    private String name;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("id")
    private String tv_show_id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("genres")
    private List<Genres> genres = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTv_show_id() {
        return tv_show_id;
    }

    public void setTv_show_id(String tv_show_id) {
        this.tv_show_id = tv_show_id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
