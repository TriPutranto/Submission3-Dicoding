package com.example.utaputranto.thirdsubmission.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.utaputranto.thirdsubmission.view.activity.MainActivity;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.adapter.MovieAdapter;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;
import com.example.utaputranto.thirdsubmission.model.movie.MovieResponse;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.utaputranto.thirdsubmission.util.utility.SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ArrayList<Movie> data;
    private MovieAdapter adapter;
    private Call<MovieResponse> call;
    private RecyclerView recyclerView;
    private ProgressBar pgBar;
    private String myDataFromActivity;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        pgBar = view.findViewById(R.id.pg_loading);
        recyclerView = view.findViewById(R.id.rv_searchMovie);

        MainActivity activity = (MainActivity) getActivity();
        myDataFromActivity = activity.getMyData();
        pgBar.setVisibility(View.GONE);
        initView();

        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelableArrayList("search");

            if (data == null) {
                getData();
            } else {
                adapter = new MovieAdapter(getContext(), data);
                recyclerView.setAdapter(adapter);
            }
        } else {
            getData();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (data == null) {
            getData();

        } else {
            outState.putParcelableArrayList("search", new ArrayList<>(data));
        }
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new VegaLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
    }

    public void getData() {
        call = SERVICE.getSearchMovie(myDataFromActivity);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                data = movieResponse.getResult();
                Log.e("movie size", String.valueOf(data.size()));

                for (int i = 0; i < data.size(); i++) {
                    Movie movie = data.get(i);
                    Log.i("Movie", "Result : " + movie.getTitle());
                    Log.i("id", "movie : " + movie.getMovieId());
                }

                pgBar.setVisibility(View.GONE);
                adapter = new MovieAdapter(getContext(), data);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
