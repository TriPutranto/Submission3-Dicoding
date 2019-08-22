package com.example.utaputranto.thirdsubmission.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;

public class TvShow implements Parcelable {

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

    @SerializedName("vote_average")
    private String vote_average;

    @SerializedName("popularity")
    private String popularity;

    private int idTvShow;

    public int getIdTvShow() {
        return idTvShow;
    }

    public void setIdTvShow(int idTvShow) {
        this.idTvShow = idTvShow;
    }

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

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.poster_path);
        dest.writeString(this.tv_show_id);
        dest.writeString(this.overview);
        dest.writeString(this.original_language);
        dest.writeString(this.vote_average);
        dest.writeString(this.popularity);
    }

    public TvShow() {
    }

    protected TvShow(Parcel in) {
        this.name = in.readString();
        this.backdrop_path = in.readString();
        this.poster_path = in.readString();
        this.tv_show_id = in.readString();
        this.overview = in.readString();
        this.original_language = in.readString();
        this.vote_average = in.readString();
        this.popularity = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
