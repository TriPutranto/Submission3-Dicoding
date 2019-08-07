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
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.AUTHORITY;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.CONTENT_URI;


public class MovieProvider extends ContentProvider {

    private static final int FAVORITE=100;
    private static final int FAVORITE_ID=101;
    private MovieFavHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE,FAVORITE);
        sUriMatcher.addURI(AUTHORITY,DatabaseContract.TABLE_MOVIE + "/#",FAVORITE_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteHelper=new MovieFavHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                cursor=favoriteHelper.queryProvider();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor= null;
                break;
        }
        if(cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                added=favoriteHelper.insertProvider(values);
                break;
            default:
                added=0;
                break;
        }
        if (added>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case FAVORITE_ID:
                deleted=favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted=0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case FAVORITE_ID:
                updated=favoriteHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated=0;
                break;
        }
        if (updated>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return updated;
    }
}