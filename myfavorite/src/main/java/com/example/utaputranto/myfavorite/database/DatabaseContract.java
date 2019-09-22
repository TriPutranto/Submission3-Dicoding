package com.example.utaputranto.myfavorite.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String AUTHORITY = "com.example.utaputranto.thirdsubmission";

    public static final class FavoriteColumns implements BaseColumns{
        public static final String TABLE_FAVORITE = "db_favorite";
        public static final String TITLE = "title";
        public static final String POSTER = "poster";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();

        public static String getColumnString(Cursor cursor, String columnName) {
            return cursor.getString(cursor.getColumnIndex(columnName));
        }
    }
}
