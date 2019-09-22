package com.example.utaputranto.myfavorite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.utaputranto.myfavorite.R;

import static com.example.utaputranto.myfavorite.database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.utaputranto.myfavorite.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.utaputranto.myfavorite.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.example.utaputranto.myfavorite.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.utaputranto.myfavorite.database.DatabaseContract.FavoriteColumns.getColumnString;

public class FavoritAdapter extends CursorAdapter {
    private TextView tvTitle, tvDate, tvDescription;
    private ImageView imgPoster;
    private String urlPhoto = "http://image.tmdb.org/t/p/original/";
    public FavoritAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            tvTitle = view.findViewById(R.id.tv_item_title);
            tvDate = view.findViewById(R.id.tv_item_date);
            tvDescription = view.findViewById(R.id.tv_item_description);
            imgPoster = view.findViewById(R.id.img_fav);

            tvTitle.setText(getColumnString(cursor, TITLE));
            tvDescription.setText(getColumnString(cursor, OVERVIEW));
            tvDate.setText(getColumnString(cursor,RELEASE_DATE));
            Glide.with(context)
                    .load(urlPhoto + getColumnString(cursor, POSTER))
                    .into(imgPoster);

        }
    }
}
