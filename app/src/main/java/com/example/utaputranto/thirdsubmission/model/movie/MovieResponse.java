package com.example.utaputranto.thirdsubmission.model.movie;

import com.example.utaputranto.thirdsubmission.model.movie.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<Movie> result = null;

    public ArrayList<Movie> getResult() {
        return result;
    }
}
