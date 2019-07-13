package com.example.utaputranto.thirdsubmission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.details.DetailsTvShowActivity;
import com.example.utaputranto.thirdsubmission.model.TvShow;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private Context context;
    private List<TvShow> tvShows;

    public TvShowAdapter(Context context, List<TvShow> tvShows) {
        this.context = context;
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_tv_show, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TvShow tvShow = tvShows.get(i);
        String url = "https://image.tmdb.org/t/p/original";
        Glide.with(context)
                .load(url + tvShow.getPoster_path())
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.ic_refresh_black_24dp)
                .into(viewHolder.imgPoster);
        viewHolder.tvTitle.setText(tvShow.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TvShow mData = new TvShow();
                mData.setName(tvShow.getName());
                mData.setPoster_path(tvShow.getPoster_path());
                mData.setOverview(tvShow.getOverview());
                mData.setBackdrop_path(tvShow.getBackdrop_path());
                mData.setOriginal_language(tvShow.getOriginal_language());
                mData.setTv_show_id(tvShow.getTv_show_id());
                mData.setPopularity(tvShow.getPopularity());
                mData.setVote_average(tvShow.getVote_average());

                Intent intent = new Intent(context, DetailsTvShowActivity.class);
                intent.putExtra(DetailsTvShowActivity.EXTRA_DATA, mData);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
