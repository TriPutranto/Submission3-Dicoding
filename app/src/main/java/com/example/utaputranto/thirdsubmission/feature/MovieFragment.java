package com.example.utaputranto.thirdsubmission.feature;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.adapter.MovieAdapter;
import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.model.MovieResponse;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        initView();

        if (savedInstanceState != null) {
            progressBar.setVisibility(View.GONE);
            movieList = savedInstanceState.getParcelableArrayList("playing");


            if (movieList == null) {
                getNowPlaying();
            } else {
                movieAdapter = new MovieAdapter(getActivity(), movieList);
                recyclerView.setAdapter(movieAdapter);
            }

        } else {
            getNowPlaying();

        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieList == null) {
            getNowPlaying();
        } else {
            outState.putParcelableArrayList("playing", new ArrayList<>(movieList));
        }
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getNowPlaying() {
        Call<MovieResponse> call = service.getNowPlaying();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                movieList = movieResponse.getResult();
                Log.e("movie size", String.valueOf(movieList.size()));

                for (int i = 0; i < movieList.size(); i++) {
                    Movie movie = movieList.get(i);
                    Log.i("Movie", "Result : " + movie.getTitle());
                    Log.i("id", "movie : " + movie.getMovieId());
                }

                progressBar.setVisibility(View.GONE);
                movieAdapter = new MovieAdapter(getContext(), movieList);
                recyclerView.setAdapter(movieAdapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

        });
    }


}
