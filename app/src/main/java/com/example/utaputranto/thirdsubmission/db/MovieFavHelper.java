package com.example.utaputranto.thirdsubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.utaputranto.thirdsubmission.model.Movie;
import com.example.utaputranto.thirdsubmission.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.DATE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.IDMOVIE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.IMG;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.OVERVIEW;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TABLE_MOVIE;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.TITLE;
import static com.example.utaputranto.thirdsubmission.details.DetailsMovieActivity.mMovie;

public class MovieFavHelper {
    private static  String DATABASE_TABLE = TABLE_MOVIE;
    public Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static MovieFavHelper INSTANCE;


    public MovieFavHelper(Context context) {
        this.context = context;
    }

    public MovieFavHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }


    public static MovieFavHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieFavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Movie> getAllNotes(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0){
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
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, movie.getTitle());
        Log.e("ini", "masuk" + mMovie.getTitle());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(IMG, movie.getPoster_path());
        initialValues.put(DATE, movie.getRelease_date());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id){
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC ");
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,
                null,
                _ID +" = ? ",
                new String[]{id},
                null,
                null,
                null);
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values) ;
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID + " = ? ",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }

}
