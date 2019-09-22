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
import com.example.utaputranto.thirdsubmission.adapter.TvShowAdapter;
import com.example.utaputranto.thirdsubmission.model.tvshow.TvShow;
import com.example.utaputranto.thirdsubmission.model.tvshow.TvShowResponse;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.utaputranto.thirdsubmission.util.utility.SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTvFragment extends Fragment {

    private ArrayList<TvShow> data;
    private TvShowAdapter adapter;
    private Call<TvShowResponse> call;
    private RecyclerView recyclerView;
    private ProgressBar pgBar;
    private String myDataFromActivity;

    public SearchTvFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        pgBar = view.findViewById(R.id.pg_loading);
        recyclerView = view.findViewById(R.id.rv_searchMovie);

        MainActivity activity = (MainActivity)getActivity();
        myDataFromActivity = activity.getMyData();
        pgBar.setVisibility(View.GONE);
        initView();

        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelableArrayList("search");

            if (data == null) {
                getData();
            } else {
                adapter = new TvShowAdapter(getContext(), data);
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

    public void getData(){
        call = SERVICE.getSearchTv(myDataFromActivity);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvResponse = response.body();
                data = tvResponse.getResult();
                Log.e("movie size", String.valueOf(data.size()));

                for (int i = 0; i < data.size(); i++) {
                    TvShow tvShow = data.get(i);
                    Log.i("Movie", "Result : " + tvShow.getName());
                    Log.i("id", "movie : " + tvShow.getTv_show_id());
                }

                pgBar.setVisibility(View.GONE);
                adapter = new TvShowAdapter(getContext(), data);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
