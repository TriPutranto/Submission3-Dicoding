package com.example.utaputranto.thirdsubmission.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.model.movie.Movie;

import java.util.concurrent.ExecutionException;

import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.CONTENT_URI;
import static com.example.utaputranto.thirdsubmission.util.utility.IMAGE_URL;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor list;

    public StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (list != null) {
            list.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null);
        Binder.restoreCallingIdentity(identityToken);


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie item = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(IMAGE_URL + item.getPoster_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            Log.e("Widgetnya ", "adalah" + item.getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        rv.setImageViewBitmap(R.id.imageViewWidget, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavouriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new Movie(list);
    }
}
