package com.example.utaputranto.thirdsubmission.details;

import android.content.ContentValues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.MainActivity;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.database.TvShowHelper;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsTvShowActivity extends AppCompatActivity {

    private ImageView imgPoster, imgBackdrop, btnFav;
    private TextView tvTitle, tvPopulatity, tvScore,
            tvLanguage, tvOverview;
    private String tvShowId;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);
    private TvShow tvShow;
    private ProgressBar progressBar;
    public static String EXTRA_DATA = "extra_data";
    private TvShow mTvShow;
    private String url = "https://image.tmdb.org/t/p/original/";

    private TvShowHelper tvShowHelper;
    private boolean isFavorite = false;


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
        btnFav = findViewById(R.id.btn_favorit);
        getDetails();

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTvShow();
                if (isFavorite) {
                    removeTvShow();
                } else {
                    isFavorite = !isFavorite;
                    setFavorite();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tvShowHelper != null) tvShowHelper.close();
    }

    public void setFavorite() {
        if (isFavorite) btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        else btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    public void saveTvShow() {
        long result = tvShowHelper.insertTvshow(mTvShow);
        if (result > 0) {
            Toast.makeText(DetailsTvShowActivity.this, getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsTvShowActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(DetailsTvShowActivity.this, getResources().getString(R.string.already_exist), Toast.LENGTH_SHORT).show();
        }
    }

    private void removeTvShow() {
        Log.e("insert", "delete" + mTvShow.getName());
        long result = tvShowHelper.deleteTvshow(tvShow.getName());
        if (result > 0) {
            Toast.makeText(DetailsTvShowActivity.this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsTvShowActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(DetailsTvShowActivity.this, getResources().getString(R.string.cant_delete), Toast.LENGTH_SHORT).show();
        }
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
