package com.example.utaputranto.thirdsubmission;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.utaputranto.thirdsubmission.adapter.MovieAdapter;
import com.example.utaputranto.thirdsubmission.adapter.MovieFavAdapter;
import com.example.utaputranto.thirdsubmission.database.MoviesHelper;
import com.example.utaputranto.thirdsubmission.db.MovieFavHelper;
import com.example.utaputranto.thirdsubmission.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavFragment extends Fragment implements LoadMovieCallback {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private RecyclerView rvFav;
    private Cursor list;
    private MovieAdapter adapter;
    private MoviesHelper movieFavHelper;
    private Context context;


    public MoviesFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_fav, container, false);
        context = view.getContext();


        rvFav = view.findViewById(R.id.rv_movie_fav);
        rvFav.setLayoutManager(new LinearLayoutManager(context));
        rvFav.setHasFixedSize(true);


        movieFavHelper = MoviesHelper.getInstance(getContext());

        movieFavHelper.open();

        //setupList();
        if (savedInstanceState == null) {
            new LoadMoviesAsync(movieFavHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                Toast.makeText(getActivity(), list.size(), Toast.LENGTH_SHORT).show();
                //adapter.setListNote(list);
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupList() {
        rvFav.setLayoutManager(new LinearLayoutManager(context));
        rvFav.setHasFixedSize(true);
        rvFav.setAdapter(adapter);
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        Toast.makeText(context, movies.get(0).getOverview(), Toast.LENGTH_SHORT).show();
        //adapter.setListFav(movies);
        adapter = new MovieAdapter(getActivity(),movies);
        rvFav.setAdapter(adapter);

    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<MoviesHelper> weakNoteHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMoviesAsync(MoviesHelper noteHelper, LoadMovieCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllNotes();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }
}
