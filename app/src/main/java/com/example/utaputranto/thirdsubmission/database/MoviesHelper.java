package com.example.utaputranto.thirdsubmission.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.utaputranto.thirdsubmission.model.Movie;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.BACKDROP;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.DATE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.DESCRIPTION;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.LANGUANGE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.POPULARITY;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.TITLE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.VOTE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.TABLE_NOTE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.IMG;

public class MoviesHelper {

    private static final String DATABASE_TABLE = TABLE_NOTE;
    private static DbHelper databaseHelper;
    private static MoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    private MoviesHelper(Context context) {
        databaseHelper = new DbHelper(context);
    }

    public static MoviesHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovie(){
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
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));
                movie.setMovieId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                movie.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                movie.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));
                movie.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                movie.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(LANGUANGE)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie movie){
        ContentValues args = new ContentValues();
        args.put(TITLE, movie.getTitle());
        args.put(DESCRIPTION, movie.getOverview());
        args.put(DATE, movie.getRelease_date());
        args.put(IMG, movie.getPoster_path());
        args.put(BACKDROP, movie.getBackdrop_path());
        args.put(VOTE, movie.getVote_average());
        args.put(POPULARITY, movie.getPopularity());
        args.put(LANGUANGE, movie.getOriginal_language());
        args.put(ID, movie.getMovieId());
        args.put(_ID, movie.getIdMovie());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(String id){
        return database.delete(TABLE_NOTE, TITLE + " = '" + id + "'", null);
    }
}
