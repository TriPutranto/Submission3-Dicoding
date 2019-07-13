package com.example.utaputranto.thirdsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<Movie> result=null;

    public ArrayList<Movie> getResult() {
        return result;
    }
}
