package com.example.utaputranto.thirdsubmission;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.utaputranto.thirdsubmission.adapter.TvShowFavAdapter;

import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment {
    private RecyclerView rvFav;
    private Cursor list;
    private TvShowFavAdapter adapter;


    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);

        // Inflate the layout for this fragment

        rvFav = view.findViewById(R.id.rv_fav);
        rvFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFav.setHasFixedSize(true);

        adapter = new TvShowFavAdapter(getActivity(), getContext());
        adapter.setListFav(list);
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
            return getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            adapter.setListFav(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0) {
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

