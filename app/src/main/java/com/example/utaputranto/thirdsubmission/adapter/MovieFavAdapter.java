package com.example.utaputranto.thirdsubmission.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.MainActivity;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.Movie;

import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.ViewHolder> {
    private Cursor listFav;
    private Activity activity;
    private Context context;

    public MovieFavAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void setListFav(Cursor listFav) {
        this.listFav = listFav;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fav, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Movie movie = getItem(i);
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvRelease.setText(movie.getRelease_date());
        viewHolder.tvOverview.setText(movie.getOverview());
        Glide.with(activity)
                .load("http://image.tmdb.org/t/p/w780/" + movie.getPoster_path())
                .into(viewHolder.imgPoster);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.getContentResolver().delete(
                        Uri.parse(CONTENT_URI + "/" + movie.getIdMovie()),
                        null,
                        null);
                Toast.makeText(activity, R.string.deleted, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listFav == null) return 0;
        return listFav.getCount();
    }

    private Movie getItem(int position) {
        if (!listFav.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listFav);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, delete;
        TextView tvTitle, tvRelease, tvOverview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_release);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
