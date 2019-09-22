package com.example.utaputranto.thirdsubmission.view.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.adapter.FavoriteAdapter;
import com.stone.vega.library.VegaLayoutManager;

import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private Cursor list;
    private FavoriteAdapter adapter;
    private RecyclerView rvFav;
    private Context context;


    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_fav, container, false);
        context = view.getContext();

        rvFav = view.findViewById(R.id.rv_movie_fav);

        rvFav.setLayoutManager(new VegaLayoutManager());
        rvFav.setHasFixedSize(true);

        adapter = new FavoriteAdapter(getActivity(), getContext());
        rvFav.setAdapter(adapter);

        new LoadFavorite().execute();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private class LoadFavorite extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getApplicationContext().getContentResolver()
                    .query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);

            list = notes;
            adapter.setListFavorite(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0) {
            }
        }
    }
}
