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
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.DATE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.DESCRIPTION;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns.TITLE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.TABLE_NOTE;

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
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                movie.setMovieId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertNote(Movie movie){
        ContentValues args = new ContentValues();
        args.put(TITLE, movie.getTitle());
        args.put(DESCRIPTION, movie.getOverview());
        args.put(DATE, movie.getRelease_date());

        args.put(ID, movie.getMovieId());
        return database.insert(DATABASE_TABLE, null, args);
    }

//    public int updateNote(Note note){
//        ContentValues args = new ContentValues();
//        args.put(TITLE, note.getTitle());
//        args.put(DESCRIPTION, note.getDescription());
//        args.put(DATE, note.getDate());
//        return database.update(DATABASE_TABLE, args, _ID + "= '" + note.getId() + "'",null);
//    }

    public int deleteNote(String title){
        return database.delete(TABLE_NOTE, TITLE + " = '" + title + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
