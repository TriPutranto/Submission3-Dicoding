package com.example.utaputranto.thirdsubmission.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.example.utaputranto.thirdsubmission.MainActivity;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.TvShow;


import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI_TV;

public class TvShowFavAdapter extends RecyclerView.Adapter<TvShowFavAdapter.ViewHolder> {
    private Cursor listFav;
    private Activity activity;
    private Context context;


    public TvShowFavAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void setListFav(Cursor listFav) {
        this.listFav = listFav;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fav_tv, viewGroup, false);
        return new TvShowFavAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TvShow tvShow = getItem(i);
        viewHolder.tvTitle.setText(tvShow.getName());
        Log.e("Fav", "Name" + tvShow.getName());
        viewHolder.tvOverview.setText(tvShow.getOverview());
        Glide.with(activity)
                .load("http://image.tmdb.org/t/p/w780/" + tvShow.getPoster_path())
                .into(viewHolder.imgPoster);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getContentResolver().delete(
                        Uri.parse(CONTENT_URI_TV + "/" + tvShow.getIdTvShow()),
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

    private TvShow getItem(int position) {
        if (!listFav.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new TvShow(listFav);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, delete;
        TextView tvTitle, tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
