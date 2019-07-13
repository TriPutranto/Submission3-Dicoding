package com.example.utaputranto.thirdsubmission.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsTvShowActivity extends AppCompatActivity {

    private ImageView imgPoster, imgBackdrop;
    private TextView tvTitle, tvPopulatity, tvScore,
            tvLanguage, tvOverview;
    private String tvShowId;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);
    private TvShow tvShow;
    private ProgressBar progressBar;
    public static String EXTRA_DATA = "extra_data";
    private TvShow mTvShow;
    private String url = "https://image.tmdb.org/t/p/original/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tv_show);

        mTvShow = getIntent().getParcelableExtra(EXTRA_DATA);
        tvShowId = mTvShow.getTv_show_id();

        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvTitle = findViewById(R.id.tv_title);
        tvPopulatity = findViewById(R.id.tv_popularity);
        tvLanguage = findViewById(R.id.tv_language);
        tvOverview = findViewById(R.id.tv_overview);
        tvScore = findViewById(R.id.tv_score);
        progressBar = findViewById(R.id.progress_bar);

        getDetails();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState == null) {
            getDetails();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {

            progressBar.setVisibility(View.GONE);
            tvTitle.setText(mTvShow.getName());
            setTitle(mTvShow.getName());
            tvOverview.setText(mTvShow.getOverview());
            tvLanguage.setText(mTvShow.getOriginal_language());
            tvScore.setText(mTvShow.getVote_average());
            tvPopulatity.setText(mTvShow.getPopularity());

            Glide.with(this)
                    .load(url + mTvShow.getPoster_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .error(R.drawable.ic_refresh_black_24dp)
                    .into(imgPoster);
            Glide.with(this)
                    .load(url + mTvShow.getBackdrop_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .into(imgBackdrop);

        } else {
            getDetails();
        }
    }

    private void getDetails() {
        Call<TvShow> call = service.getDetailTv(tvShowId);
        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful()) {
                    tvShow = response.body();
                    Log.e("data details : ", tvShow.getName() + "");
                    setTitle(tvShow.getName());
                    tvTitle.setText(tvShow.getName());
                    tvOverview.setText(tvShow.getOverview());
                    tvLanguage.setText(tvShow.getOriginal_language());
                    tvScore.setText(tvShow.getVote_average());
                    tvPopulatity.setText(tvShow.getPopularity());

                    Glide.with(getApplicationContext())
                            .load(url + tvShow.getPoster_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgPoster);
                    Glide.with(getApplicationContext())
                            .load(url + tvShow.getBackdrop_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgBackdrop);

                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                Toast.makeText(DetailsTvShowActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
