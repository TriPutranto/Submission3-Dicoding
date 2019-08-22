package com.example.utaputranto.thirdsubmission;

import com.example.utaputranto.thirdsubmission.model.TvShow;

import java.util.ArrayList;

public interface LoadTvshowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvShows);
}
