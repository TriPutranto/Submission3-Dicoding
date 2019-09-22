package com.example.utaputranto.thirdsubmission.view.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.BACKDROP;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CATEGORY;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CONTENT_URI;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.RATING;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.RELEASE_DATE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.OVERVIEW;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.POSTER;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.TITLE;
import static com.example.utaputranto.thirdsubmission.util.utility.IMAGE_URL;
import static com.example.utaputranto.thirdsubmission.util.utility.SERVICE;
import static com.example.utaputranto.thirdsubmission.widget.FavouriteWidget.sendRefreshBroadcast;

public class DetailsMovieActivity extends AppCompatActivity {

    private ImageView imgPoster, imgBackdrop, btnFav;
    private TextView tvTitle, tvRelease, tvScore,
            tvPopularity, tvOverview, tvLanguage;
    private Movie movie;
    public static Movie mMovie;
    private String movieId;
    public static String EXTRA_DATA = "extra_data";
    private ProgressBar progressBar;
    private Call<Movie> call;
    private int id;
    private ScrollView frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        mMovie = getIntent().getParcelableExtra(EXTRA_DATA);
        movieId = mMovie.getMovieId();

        imgPoster = findViewById(R.id.img_poster);
        frameLayout =findViewById(R.id.frame_layout);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release);
        tvScore = findViewById(R.id.tv_score);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvOverview = findViewById(R.id.tv_overview);
        progressBar = findViewById(R.id.progress_bar);
        tvLanguage = findViewById(R.id.tv_language);
        btnFav = findViewById(R.id.btn_favorit);
        getDetails();
        checkFavorite();

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMovie();
            }
        });

    }

    private void SaveMovie() {
        if (checkFavorite()) {
            Uri uri = Uri.parse(CONTENT_URI + "/" + id);
            int i = getContentResolver().delete(uri, null, null);
            btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            Snackbar snackbar = Snackbar.make(frameLayout, getResources().getString(R.string.deleted), Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            ContentValues values = new ContentValues();
            values.put(TITLE, mMovie.getTitle());
            values.put(OVERVIEW, mMovie.getOverview());
            values.put(RELEASE_DATE, mMovie.getRelease_date());
            values.put(POSTER, mMovie.getPoster_path());
            values.put(RATING, mMovie.getVote_average());
            values.put(BACKDROP, mMovie.getBackdrop_path());
            values.put(ID, movieId);
            values.put(CATEGORY, "movie");

            if (getContentResolver().insert(CONTENT_URI, values) != null) {
                btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                Snackbar snackbar = Snackbar.make(frameLayout, getResources().getString(R.string.saved), Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar.make(frameLayout, getResources().getString(R.string.error_save), Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

        }
        sendRefreshBroadcast(getApplicationContext());
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
                    .load(IMAGE_URL + mMovie.getPoster_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .error(R.drawable.ic_refresh_black_24dp)
                    .into(imgPoster);
            Glide.with(this)
                    .load(IMAGE_URL + mMovie.getBackdrop_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .into(imgBackdrop);
        }
    }


    private void getDetails() {
        call = SERVICE.getDetail(movieId);
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
                            .load(IMAGE_URL + movie.getPoster_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgPoster);
                    Glide.with(getApplicationContext())
                            .load(IMAGE_URL + movie.getBackdrop_path())
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

    private boolean checkFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        int mId = Integer.parseInt(movieId);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int getmId;
        if (cursor.moveToFirst()) {
            do {
                getmId = cursor.getInt(1);
                if (getmId == mId) {
                    id = cursor.getInt(0);
                    btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;
    }
}
