package com.example.utaputranto.thirdsubmission.view.activity;

import android.content.ContentValues;

import android.database.Cursor;
import android.net.Uri;
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
import com.example.utaputranto.thirdsubmission.model.tvshow.TvShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.BACKDROP;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CATEGORY;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CONTENT_URI;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.OVERVIEW;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.POSTER;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.RATING;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.RELEASE_DATE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.TITLE;
import static com.example.utaputranto.thirdsubmission.util.utility.IMAGE_URL;
import static com.example.utaputranto.thirdsubmission.util.utility.SERVICE;
import static com.example.utaputranto.thirdsubmission.widget.FavouriteWidget.sendRefreshBroadcast;

public class DetailsTvShowActivity extends AppCompatActivity {

    private ImageView imgPoster, imgBackdrop, btnFav;
    private TextView tvTitle, tvPopulatity, tvScore,
            tvLanguage, tvOverview;
    private String tvShowId;
    private TvShow tvShow;
    private ProgressBar progressBar;
    public static String EXTRA_DATA = "extra_data";
    private TvShow mTvShow;
    private Call<TvShow> call;
    private int id;

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
        checkFavorite();

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTvShow();
            }
        });

    }


    private void saveTvShow() {
        if (checkFavorite()) {
            Uri uri = Uri.parse(CONTENT_URI + "/" + id);
            int i = getContentResolver().delete(uri, null, null);
            btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            Toast.makeText(this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();

        } else{
            ContentValues values = new ContentValues();
            values.put(TITLE, mTvShow.getName());
            values.put(OVERVIEW, mTvShow.getOverview());
            values.put(RELEASE_DATE, mTvShow.getmFirstAirDate());
            values.put(POSTER, mTvShow.getPoster_path());
            values.put(RATING, mTvShow.getVote_average());
            values.put(BACKDROP, mTvShow.getBackdrop_path());
            values.put(ID, tvShowId);
            values.put(CATEGORY, "tv");

            if (getContentResolver().insert(CONTENT_URI, values) != null){
                btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                Toast.makeText(this, getResources().getString(R.string.saved) + mTvShow.getName(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.error_save) + mTvShow.getName(), Toast.LENGTH_SHORT).show();

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
            tvTitle.setText(mTvShow.getName());
            setTitle(mTvShow.getName());
            tvOverview.setText(mTvShow.getOverview());
            tvLanguage.setText(mTvShow.getOriginal_language());
            tvScore.setText(mTvShow.getVote_average());
            tvPopulatity.setText(mTvShow.getPopularity());

            Glide.with(this)
                    .load(IMAGE_URL + mTvShow.getPoster_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .error(R.drawable.ic_refresh_black_24dp)
                    .into(imgPoster);
            Glide.with(this)
                    .load(IMAGE_URL + mTvShow.getBackdrop_path())
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .into(imgBackdrop);

        } else {
            getDetails();
        }
    }

    private void getDetails() {
        call = SERVICE.getDetailTv(tvShowId);
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
                            .load(IMAGE_URL + tvShow.getPoster_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgPoster);
                    Glide.with(getApplicationContext())
                            .load(IMAGE_URL + tvShow.getBackdrop_path())
                            .placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.drawable.ic_refresh_black_24dp)
                            .into(imgBackdrop);

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                Toast.makeText(DetailsTvShowActivity.this, getResources().getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        int tvId = Integer.parseInt(tvShowId);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int getmId;
        if (cursor.moveToFirst()) {
            do {
                getmId = cursor.getInt(1);
                if (getmId == tvId) {
                    id = cursor.getInt(0);
                    btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;
    }
}
