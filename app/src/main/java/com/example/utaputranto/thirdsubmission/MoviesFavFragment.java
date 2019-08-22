package com.example.utaputranto.thirdsubmission;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.utaputranto.thirdsubmission.adapter.MovieAdapter;
import com.example.utaputranto.thirdsubmission.database.MoviesHelper;
import com.example.utaputranto.thirdsubmission.model.Movie;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavFragment extends Fragment implements LoadMovieCallback {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private RecyclerView rvFav;
    private MovieAdapter adapter;
    private MoviesHelper movieFavHelper;
    private Context context;
    private ArrayList<Movie> movieList;


    public MoviesFavFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_fav, container, false);
        context = view.getContext();

        rvFav = view.findViewById(R.id.rv_movie_fav);
        rvFav.setLayoutManager(new LinearLayoutManager(context));
        rvFav.setHasFixedSize(true);

        movieFavHelper = MoviesHelper.getInstance(getContext());
        movieFavHelper.open();

        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (movieList == null) {
                new LoadMoviesAsync(movieFavHelper, this).execute();
            } else {
                adapter = new MovieAdapter(getActivity(), movieList);
                rvFav.setAdapter(adapter);
            }

        } else {
            new LoadMoviesAsync(movieFavHelper, this).execute();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieList == null) {
            new LoadMoviesAsync(movieFavHelper, this).execute();
        } else {
            outState.putParcelableArrayList(EXTRA_STATE, new ArrayList<>(movieList));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });

    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        Collections.reverse(movies);
        adapter = new MovieAdapter(getActivity(), movies);
        rvFav.setAdapter(adapter);
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<MoviesHelper> weakMovieHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMoviesAsync(MoviesHelper noteHelper, LoadMovieCallback callback) {
            weakMovieHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

}
