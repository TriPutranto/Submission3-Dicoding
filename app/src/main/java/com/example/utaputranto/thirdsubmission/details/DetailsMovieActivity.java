package com.example.utaputranto.thirdsubmission.details;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsMovieActivity extends AppCompatActivity {
    private ImageView imgPoster, imgBackdrop;
    private TextView tvTitle, tvRelease, tvBudget, tvGenres, tvOverview;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);
    private Movie movie;
    private String movieId;
    private ArrayList<Movie> data;
    public static String EXTRA_DATA = "extra_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release);
        tvBudget = findViewById(R.id.tv_budget);
        tvGenres = findViewById(R.id.tv_genre);
        tvOverview = findViewById(R.id.tv_overview);

        movieId = getIntent().getStringExtra("MovieId");

        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelableArrayList("now_playing");

            tvTitle.setText(movie.getTitle());
//            tvRelease.setText(mMovie.getRelease_date());
//            tvBudget.setText(mMovie.getBudget());
//            tvOverview.setText(mMovie.getOverview());
//            String nilaiGenre = "";
//
//            for (int i = 0; i < mMovie.getGenres().size(); i++) {
//
//                nilaiGenre += mMovie.getGenres().get(i).getName() + ", ";
//                tvGenres.setText(nilaiGenre);
//            }


//            Glide.with(this)
//                    .load(mMovie.getPoster_path())
//                    .placeholder(R.drawable.ic_file_download_black_24dp)
//                    .error(R.drawable.ic_refresh_black_24dp)
//                    .into(imgPoster);
//            Glide.with(this)
//                    .load(mMovie.getBackdrop_path())
//                    .placeholder(R.drawable.ic_file_download_black_24dp)
//                    .error(R.drawable.ic_refresh_black_24dp)
//                    .into(imgBackdrop);
        } else {
            getDetails();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Menyimpan data tertentu (String) ke Bundle
        if (data == null) {
            getDetails();
        }else{
            savedInstanceState.putParcelableArrayList("now_playing", new ArrayList<>(data));
        }
        // Selalu simpan pemanggil superclass di bawah agar data di view tetap tersimpan
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Selalu panggil superclass agar data di view tetap ada
        super.onRestoreInstanceState(savedInstanceState);
    }


    private void getDetails() {
        Call<Movie> call = service.getDetail(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movie = response.body();
                    String url = "https://image.tmdb.org/t/p/original/";
                    Log.e("data details : ", movie.getTitle());
                    setTitle(movie.getTitle());
                    tvTitle.setText(movie.getTitle());
                    tvRelease.setText(movie.getRelease_date());
                    tvBudget.setText("$ " + movie.getBudget());
                    tvOverview.setText(movie.getOverview());
                    String nilaiGenre = "";

                    for (int i = 0; i < movie.getGenres().size(); i++) {

                        nilaiGenre += movie.getGenres().get(i).getName() + ", ";
                        tvGenres.setText(nilaiGenre);
                    }

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


                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
}
