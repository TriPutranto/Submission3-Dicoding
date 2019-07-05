package com.example.utaputranto.thirdsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvShowResponse {

    @SerializedName("results")
    private ArrayList<TvShow> result=null;

    public ArrayList<TvShow> getResult() {
        return result;
    }

    public void setResult(ArrayList<TvShow> result) {
        this.result = result;
    }
}
