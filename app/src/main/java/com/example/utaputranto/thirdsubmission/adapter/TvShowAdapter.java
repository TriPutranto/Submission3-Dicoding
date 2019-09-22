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
import com.example.utaputranto.thirdsubmission.view.activity.DetailsTvShowActivity;
import com.example.utaputranto.thirdsubmission.model.tvshow.TvShow;

import java.util.ArrayList;

import static com.example.utaputranto.thirdsubmission.util.utility.IMAGE_URL;


public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TvShow> tvShows;

    public TvShowAdapter(Context context, ArrayList<TvShow> tvShows) {
        this.context = context;
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fav_tv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TvShow tvShow = tvShows.get(i);
        Glide.with(context)
                .load(IMAGE_URL + tvShow.getPoster_path())
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.ic_refresh_black_24dp)
                .into(viewHolder.imgPoster);
        viewHolder.tvTitle.setText(tvShow.getName());
        viewHolder.tvOverview.setText(tvShow.getOverview());
        viewHolder.tvRelease.setText(tvShow.getmFirstAirDate());
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
                mData.setmFirstAirDate(tvShow.getmFirstAirDate());
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
        TextView tvTitle, tvOverview, tvRelease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvRelease = itemView.findViewById(R.id.tv_release);

        }
    }
}
