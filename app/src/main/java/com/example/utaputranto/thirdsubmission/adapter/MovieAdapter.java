package com.example.utaputranto.thirdsubmission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.details.DetailsMovieActivity;
import com.example.utaputranto.thirdsubmission.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Movie movie = movies.get(i);
        String url = "https://image.tmdb.org/t/p/original";
        Glide.with(context)
                .load(url + movie.getPoster_path())
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.ic_refresh_black_24dp)
                .into(viewHolder.imgPoster);
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvRelease.setText(movie.getRelease_date());
        viewHolder.tvOverview.setText(movie.getOverview());
        Log.e("overview", "moview" + movie.getOverview());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie mData = new Movie();
                mData.setMovieId(movie.getMovieId());
                mData.setTitle(movie.getTitle());
                mData.setPoster_path(movie.getPoster_path());
                mData.setBackdrop_path(movie.getBackdrop_path());
                mData.setOverview(movie.getOverview());
                mData.setVote_average(movie.getVote_average());
                mData.setRelease_date(movie.getRelease_date());
                mData.setPopularity(movie.getPopularity());
                mData.setOriginal_language(movie.getOriginal_language());

                Toast.makeText(context, movie.getMovieId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailsMovieActivity.class);
                intent.putExtra(DetailsMovieActivity.EXTRA_DATA, mData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvRelease, tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_release);
            tvOverview = itemView.findViewById(R.id.tv_overview);

        }
    }
}
