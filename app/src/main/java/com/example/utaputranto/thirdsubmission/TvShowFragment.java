package com.example.utaputranto.thirdsubmission;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.utaputranto.thirdsubmission.adapter.TvShowAdapter;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import com.example.utaputranto.thirdsubmission.model.TvShowResponse;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private List<TvShow> tvShowsList;
    private TvShowAdapter tvShowAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);


    public TvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_tv_show);
        progressBar = view.findViewById(R.id.progress_bar);

        initView();
        getPopularTvShow();
    }


    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
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

                for (int i = 0; i < tvShowsList.size(); i++){
                    TvShow tvShow = tvShowsList.get(i);
                    Log.i("TvShow", "Result : " + tvShow.getName());
                }
                progressBar.setVisibility(View.GONE);
                tvShowAdapter = new TvShowAdapter(getContext(), tvShowsList);
                recyclerView.setAdapter(tvShowAdapter);

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }


}
