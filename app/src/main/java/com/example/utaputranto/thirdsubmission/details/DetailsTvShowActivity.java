package com.example.utaputranto.thirdsubmission.details;

import android.content.ContentValues;
import android.content.Intent;
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
import com.example.utaputranto.thirdsubmission.db.DatabaseContract;
import com.example.utaputranto.thirdsubmission.db.MovieFavHelper;
import com.example.utaputranto.thirdsubmission.db.TvShowFavHelper;
import com.example.utaputranto.thirdsubmission.feature.MovieFragment;
import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.model.TvShow;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI_TV;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.DATE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TvShowColumns.IDMOVIE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TvShowColumns.IMG;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TvShowColumns.OVERVIEW;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TvShowColumns.TITLE;

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

    private TvShowFavHelper tvShowFavHelper;
    private int favorite;
    private boolean isFavorite = false;
    public  String IS_FAVORITE = "is_favorite";
    private int idsql;
    public static String IDSQL = "idsql";
    private String id;

    TvShowFavHelper db;
    public static final String EXTRA_NOTE = "extra_note";



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

        //add
        tvShowFavHelper = new TvShowFavHelper(this);
        tvShowFavHelper.open();

//        Uri uri = getIntent().getData();
//
//        if (uri != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//
//            if (cursor != null) {
//                if (cursor.moveToNext()) tvShow = new TvShow(cursor);
//                cursor.close();
//            }
//        }
//        favorite = getIntent().getIntExtra(IS_FAVORITE, 0);
//        if (favorite == 1) {
//            isFavorite = true;
//            btnFav.setImageResource(R.drawable.ic_favorite_black_24dp);
//        }
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tvShowFavHelper.insert(tvShow);
//                if (!isFavorite) {
                    addFavorite();
                    Toast.makeText(DetailsTvShowActivity.this, R.string.addtofavorite, Toast.LENGTH_SHORT).show();
//                } else {
//                    deleteFavorite();
//                    Toast.makeText(DetailsTvShowActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
//                }


            }
        });
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

    private void addFavorite() {
        ContentValues values = new ContentValues();
        values.put(TITLE, mTvShow.getName());
        values.put(OVERVIEW, mTvShow.getOverview());
        values.put(IMG, mTvShow.getPoster_path());
        values.put(IDMOVIE, tvShowId);
        getContentResolver().insert(CONTENT_URI_TV, values);
        finish();
    }

    private void deleteFavorite() {
        idsql = getIntent().getIntExtra(IDSQL, 0);
        getContentResolver().delete(
                Uri.parse(CONTENT_URI_TV+ "/" + idsql),
                null,
                null);
        Intent intent = new Intent(DetailsTvShowActivity.this, MovieFragment.class);
        startActivity(intent);
        finish();

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
