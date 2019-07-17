package com.example.utaputranto.thirdsubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.utaputranto.thirdsubmission.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.DATE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.IMG;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.OVERVIEW;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.TITLE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TABLE_CATALOG;

public class MovieFavHelper {
    private static  String DATABASE_TABLE = TABLE_CATALOG;
    public Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieFavHelper(Context context) {
        this.context = context;
    }

    public MovieFavHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Movie> query(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor =  database.query(DATABASE_TABLE,null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToNext();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setIdMovie(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie) {
        ContentValues initialValue = new ContentValues();
        initialValue.put(TITLE, movie.getTitle());
        initialValue.put(OVERVIEW, movie.getOverview());
        initialValue.put(DATE, movie.getRelease_date());
        initialValue.put(IMG,movie.getPoster_path());
        return database.insert(DATABASE_TABLE, null, initialValue);
    }

    public int update(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(DATE, movie.getRelease_date());
        args.put(IMG,movie.getPoster_path());

        return database.update(DATABASE_TABLE,args, _ID + "= '" +movie.getMovieId()+"'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_CATALOG, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " + ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
