package com.example.utaputranto.thirdsubmission.feature;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.adapter.TvShowAdapter;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import com.example.utaputranto.thirdsubmission.model.TvShowResponse;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private ArrayList<TvShow> tvShowsList;
    private TvShowAdapter tvShowAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        recyclerView = view.findViewById(R.id.rv_tv_show);
        progressBar = view.findViewById(R.id.progress_bar);
        initView();

        if (savedInstanceState != null) {
            progressBar.setVisibility(View.GONE);
            tvShowsList = savedInstanceState.getParcelableArrayList("playing");


            if (tvShowsList == null) {
                getPopularTvShow();
            } else {
                tvShowAdapter = new TvShowAdapter(getActivity(), tvShowsList);
                recyclerView.setAdapter(tvShowAdapter);
            }

        } else {
            getPopularTvShow();

        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tvShowsList == null) {
            getPopularTvShow();
        } else {
            outState.putParcelableArrayList("playing", new ArrayList<>(tvShowsList));
        }
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getPopularTvShow() {
        Call<TvShowResponse> call = service.getTvPopular();
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();
                tvShowsList = tvShowResponse.getResult();
                Log.e("tv size", String.valueOf(tvShowsList.size()));

                for (int i = 0; i < tvShowsList.size(); i++) {
                    TvShow tvShow = tvShowsList.get(i);
                    Log.i("TvShow", "Result : " + tvShow.getName());
                }
                progressBar.setVisibility(View.GONE);
                tvShowAdapter = new TvShowAdapter(getContext(), tvShowsList);
                recyclerView.setAdapter(tvShowAdapter);

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
