package com.example.utaputranto.thirdsubmission.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
    private TextView tvTitle, tvGenre,
            tvLanguage, tvOverview;
    private String tvShowId;
    final ApiService service = RetrofitClient.retrofit().create(ApiService.class);
    private TvShow tvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tv_show);

        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        tvLanguage = findViewById(R.id.tv_language);
        tvOverview = findViewById(R.id.tv_overview);

        tvShowId = getIntent().getStringExtra("TvShowId");
        Toast.makeText(this, tvShowId, Toast.LENGTH_SHORT).show();
        getDetails();

    }




    private void getDetails() {
        Call<TvShow> call = service.getDetailTv(tvShowId);
        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful()) {
                    tvShow = response.body();
                    String url = "https://image.tmdb.org/t/p/original/";
                    Log.e("data details : ", tvShow.getName()+"");
                    setTitle(tvShow.getName());
                    tvTitle.setText(tvShow.getName());
                    tvOverview.setText(tvShow.getOverview());
                    tvLanguage.setText(tvShow.getOriginal_language());
                    String nilaiGenre = "";

                    for (int i = 0; i < tvShow.getGenres().size(); i++){
                        nilaiGenre += tvShow.getGenres().get(i).getName() + ", ";
                        tvGenre.setText(nilaiGenre);
                    }

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

                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                Toast.makeText(DetailsTvShowActivity.this, "on fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
