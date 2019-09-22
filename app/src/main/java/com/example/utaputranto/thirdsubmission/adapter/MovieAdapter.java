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

import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.view.activity.DetailsMovieActivity;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;

import java.util.ArrayList;

import static com.example.utaputranto.thirdsubmission.util.utility.IMAGE_URL;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
        notifyDataSetChanged();
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
        Glide.with(context)
                .load(IMAGE_URL + movie.getPoster_path())
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
                mData.setIdMovie(movie.getIdMovie());
                mData.setPoster_path(movie.getPoster_path());
                mData.setBackdrop_path(movie.getBackdrop_path());
                mData.setRelease_date(movie.getRelease_date());
                mData.setOverview(movie.getOverview());
                mData.setOriginal_language(movie.getOriginal_language());
                mData.setPopularity(movie.getPopularity());
                mData.setVote_average(movie.getVote_average());
                mData.setTitle(movie.getTitle());

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
