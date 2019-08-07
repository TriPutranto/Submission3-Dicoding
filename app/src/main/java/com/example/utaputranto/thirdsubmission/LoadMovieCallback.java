package com.example.utaputranto.thirdsubmission;

import com.example.utaputranto.thirdsubmission.model.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
