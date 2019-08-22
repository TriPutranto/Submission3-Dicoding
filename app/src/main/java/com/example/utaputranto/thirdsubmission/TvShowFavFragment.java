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
import com.example.utaputranto.thirdsubmission.adapter.TvShowAdapter;
import com.example.utaputranto.thirdsubmission.database.TvShowHelper;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment implements LoadTvshowCallback {

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private RecyclerView rvFav;
    private TvShowAdapter adapter;
    private TvShowHelper tvShowHelper;
    private Context context;
    private ArrayList<TvShow> tvShowList;

    public TvShowFavFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
        context = view.getContext();

        rvFav = view.findViewById(R.id.rv_fav_tv);
        rvFav.setLayoutManager(new LinearLayoutManager(context));
        rvFav.setHasFixedSize(true);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();

        if (savedInstanceState != null) {
            tvShowList = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (tvShowList == null) {
                new LoadTvShowAsync(tvShowHelper, this).execute();
            } else {
                adapter = new TvShowAdapter(getActivity(), tvShowList);
                rvFav.setAdapter(adapter);
            }

        } else {
            new LoadTvShowAsync(tvShowHelper, this).execute();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tvShowList == null) {
            new LoadTvShowAsync(tvShowHelper, this).execute();
        } else {
            outState.putParcelableArrayList(EXTRA_STATE, new ArrayList<>(tvShowList));
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
    public void postExecute(ArrayList<TvShow> tvShows) {
        Collections.reverse(tvShows);
        adapter = new TvShowAdapter(getActivity(), tvShows);
        rvFav.setAdapter(adapter);
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {
        private final WeakReference<TvShowHelper> weakTvHelper;
        private final WeakReference<LoadTvshowCallback> weakCallback;

        private LoadTvShowAsync(TvShowHelper tvHelper, LoadTvshowCallback callback) {
            weakTvHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return weakTvHelper.get().getAllTvshow();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tv) {
            super.onPostExecute(tv);
            weakCallback.get().postExecute(tv);
        }
    }
}

