package com.example.utaputranto.thirdsubmission.view.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.adapter.MovieAdapter;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;
import com.example.utaputranto.thirdsubmission.model.movie.MovieResponse;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.utaputranto.thirdsubmission.util.utility.SERVICE;


public class MovieFragment extends Fragment {
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView errorNetwork;
    private FrameLayout frameLayout;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        errorNetwork = view.findViewById(R.id.error_network);
        frameLayout = view.findViewById(R.id.frame_layout);
        checkConnection();
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

    private void checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) Objects.requireNonNull(getContext())
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            showError();
        }
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new VegaLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getNowPlaying() {
        Call<MovieResponse> call = SERVICE.getNowPlaying();
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

    private void showError() {
        Snackbar snackbar = Snackbar.make(frameLayout, getResources().getString(R.string.offline), Snackbar.LENGTH_SHORT);
        snackbar.show();
        progressBar.setVisibility(View.GONE);
        errorNetwork.setVisibility(View.VISIBLE);
    }
}
