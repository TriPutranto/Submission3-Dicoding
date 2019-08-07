package com.example.utaputranto.thirdsubmission.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.IDMOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbcatalog";
    private static final int DATABASE_VERSION = 13;


    private static final String SQL_CREATE_TABLE_TVSHOWS = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.CatalogColumns._ID,
            DatabaseContract.CatalogColumns.TITLE,
            DatabaseContract.CatalogColumns.OVERVIEW,
            DatabaseContract.CatalogColumns.IMG,
            IDMOVIE
    );

    private static final String SQL_CREATE_TABLE_MOVIES = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.CatalogColumns._ID,
            DatabaseContract.CatalogColumns.TITLE,
            DatabaseContract.CatalogColumns.OVERVIEW,
            DatabaseContract.CatalogColumns.DATE,
            DatabaseContract.CatalogColumns.IMG,
            IDMOVIE
    );


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        String SQL_CREATE_TABLE_MOVIE ="create table " +
//                TABLE_MOVIE + " (" +
//                TITLE + " text not null, " +
//                OVERVIEW + " text not null, " +
//                DATE + " text not null, " +
//                IDMOVIE + " integer primary key autoincrement, " +
//                IMG + " text not null) ;";
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_TVSHOWS);



        Log.e("Data", "onCreate: " + SQL_CREATE_TABLE_MOVIES);
        Log.e("Data", "onCreate: " + SQL_CREATE_TABLE_TVSHOWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +DatabaseContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        onCreate(db);
    }


}
