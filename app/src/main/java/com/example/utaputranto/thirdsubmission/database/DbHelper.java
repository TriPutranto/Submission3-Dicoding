package com.example.utaputranto.thirdsubmission.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.utaputranto.thirdsubmission.database.DbContract.FavoriteColumns.TABLE_FAVORITE;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "cataloguemovie";

    private static final int DATABASE_VERSION = 5;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE,
            DbContract.FavoriteColumns._ID,
            DbContract.FavoriteColumns.ID,
            DbContract.FavoriteColumns.TITLE,
            DbContract.FavoriteColumns.POSTER,
            DbContract.FavoriteColumns.BACKDROP,
            DbContract.FavoriteColumns.RATING,
            DbContract.FavoriteColumns.RELEASE_DATE,
            DbContract.FavoriteColumns.OVERVIEW,
            DbContract.FavoriteColumns.CATEGORY
    );

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }
}
