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
import com.example.utaputranto.thirdsubmission.view.activity.MainActivity;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;

import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CONTENT_URI;
import static com.example.utaputranto.thirdsubmission.util.utility.IMAGE_URL;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor listFavorite;
    private Activity activity;
    private Context context;

    public FavoriteAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void setListFavorite(Cursor listFavorite) {
        this.listFavorite = listFavorite;
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
        viewHolder.tvDate.setText(movie.getRelease_date());
        viewHolder.tvOverview.setText(movie.getOverview());
        Glide.with(context)
                .load(IMAGE_URL + movie.getPoster_path())
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.ic_refresh_black_24dp)
                .into(viewHolder.imgPoster);
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
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
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    private Movie getItem(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Invalid Position");
        }
        return new Movie(listFavorite);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, btnDelete;
        TextView tvTitle, tvDate, tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvDate = itemView.findViewById(R.id.tv_release);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
