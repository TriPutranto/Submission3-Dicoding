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
import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsMovieActivity extends AppCompatActivity {

    private ImageView imgPoster, imgBackdrop;
    private TextView tvTitle, tvRelease, tvScore,
            tvPopularity, tvOverview, tvLanguage;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);
    private Movie movie;
    private Movie mMovie;
    private String movieId;
    public static String EXTRA_DATA = "extra_data";
    private ProgressBar progressBar;
    private String url = "https://image.tmdb.org/t/p/original/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        mMovie = getIntent().getParcelableExtra(EXTRA_DATA);
        movieId = mMovie.getMovieId();

        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release);
        tvScore = findViewById(R.id.tv_score);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvOverview = findViewById(R.id.tv_overview);
        progressBar = findViewById(R.id.progress_bar);
        tvLanguage = findViewById(R.id.tv_language);

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
            setTitle(mMovie.getTitle());
            tvTitle.setText(mMovie.getTitle());
            tvOverview.setText(mMovie.getOverview());
            tvRelease.setText(mMovie.getRelease_date());
            tvScore.setText(mMovie.getVote_average());
            tvPopularity.setText(mMovie.getPopularity());
            tvLanguage.setText(mMovie.getOriginal_language());
            Glide.with(this)
                    .load(url + mMovie.getPoster_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .error(R.drawable.ic_refresh_black_24dp)
                    .into(imgPoster);
            Glide.with(this)
                    .load(url + mMovie.getBackdrop_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .into(imgBackdrop);
        }
    }

    private void getDetails() {
        Call<Movie> call = service.getDetail(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movie = response.body();
                    Log.e("data details : ", movie.getTitle());
                    setTitle(movie.getTitle());
                    tvTitle.setText(movie.getTitle());
                    tvRelease.setText(movie.getRelease_date());
                    tvScore.setText(movie.getVote_average());
                    tvOverview.setText(movie.getOverview());
                    tvPopularity.setText(movie.getPopularity());
                    tvLanguage.setText(movie.getOriginal_language());
                    Glide.with(getApplicationContext())
                            .load(url + movie.getPoster_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgPoster);
                    Glide.with(getApplicationContext())
                            .load(url + movie.getBackdrop_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgBackdrop);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(DetailsMovieActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
