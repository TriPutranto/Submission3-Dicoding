package com.example.utaputranto.thirdsubmission.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.utaputranto.thirdsubmission.db.DatabaseContract;
import com.example.utaputranto.thirdsubmission.db.MovieFavHelper;
import com.example.utaputranto.thirdsubmission.db.TvShowFavHelper;

import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.AUTHORITY;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int CATALOG = 1;
    private static final int CATALOG_ID = 2;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_CATALOG, CATALOG);


        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_CATALOG + "/#", CATALOG_ID);
    }

    private MovieFavHelper movieFavHelper;

    @Override
    public boolean onCreate() {
        movieFavHelper = new MovieFavHelper(getContext());
        movieFavHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CATALOG:
                cursor = movieFavHelper.queryProvider();
                break;
            case CATALOG_ID:
                cursor = movieFavHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;

        switch (sUriMatcher.match(uri)) {
            case CATALOG:
                added = movieFavHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case CATALOG_ID:
                deleted = movieFavHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case CATALOG_ID:
                updated = movieFavHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;

        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
