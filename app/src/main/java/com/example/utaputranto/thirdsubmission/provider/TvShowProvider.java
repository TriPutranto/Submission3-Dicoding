package com.example.utaputranto.thirdsubmission.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.utaputranto.thirdsubmission.db.DatabaseContract;
import com.example.utaputranto.thirdsubmission.db.TvShowFavHelper;

import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.AUTHORITY;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CONTENT_URI_TV;

public class TvShowProvider extends ContentProvider {

    private static final int CATALOG = 1;
    private static final int CATALOG_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_TVSHOW, CATALOG);


        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_TVSHOW + "/#", CATALOG_ID);
    }
    private TvShowFavHelper tvShowFavHelper;


    @Override
    public boolean onCreate() {
        tvShowFavHelper = new TvShowFavHelper(getContext());
        tvShowFavHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,@Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case CATALOG:
                cursor = tvShowFavHelper.queryProvider();
                break;
            case CATALOG_ID:
                cursor = tvShowFavHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@Nullable Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)){
            case CATALOG:
                added = tvShowFavHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }

    @Override
    public int delete(@Nullable Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case CATALOG_ID:
                deleted = tvShowFavHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@Nullable Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case CATALOG_ID:
                updated = tvShowFavHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
