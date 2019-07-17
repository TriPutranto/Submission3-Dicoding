package com.example.utaputranto.thirdsubmission.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbcatalog";
    private  static final int DATABASE_VERSION = 4;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_CATALOG,
            DatabaseContract.CatalogColumns._ID,
            DatabaseContract.CatalogColumns.TITLE,
            DatabaseContract.CatalogColumns.OVERVIEW,
            DatabaseContract.CatalogColumns.DATE,
            DatabaseContract.CatalogColumns.IMG,
            DatabaseContract.CatalogColumns.IDMOVIE
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.TvShowColumns._ID,
            DatabaseContract.TvShowColumns.TITLE,
            DatabaseContract.TvShowColumns.OVERVIEW,
            DatabaseContract.TvShowColumns.IMG,
            DatabaseContract.TvShowColumns.IDMOVIE
    );



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_CATALOG);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        onCreate(db);
    }
}
