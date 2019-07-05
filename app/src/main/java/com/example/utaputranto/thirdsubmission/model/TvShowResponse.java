package com.example.utaputranto.thirdsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("results")
    private List<TvShow> result=null;

    public List<TvShow> getResult() {
        return result;
    }

    public void setResult(List<TvShow> result) {
        this.result = result;
    }
}
