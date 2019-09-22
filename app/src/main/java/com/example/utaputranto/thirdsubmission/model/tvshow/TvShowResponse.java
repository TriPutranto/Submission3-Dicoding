package com.example.utaputranto.thirdsubmission.model.tvshow;

import com.example.utaputranto.thirdsubmission.model.tvshow.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowResponse {

    @SerializedName("results")
    private ArrayList<TvShow> result = null;

    public ArrayList<TvShow> getResult() {
        return result;
    }
}
