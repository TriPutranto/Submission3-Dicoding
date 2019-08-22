package com.example.utaputranto.thirdsubmission.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.utaputranto.thirdsubmission.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.BACKDROP;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.DESCRIPTION;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.ID;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.IMG;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.LANGUANGE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.POPULARITY;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.TITLE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.NoteColumns2.VOTE;
import static com.example.utaputranto.thirdsubmission.database.DbContract.TABLE_NOTE2;

public class TvShowHelper {

    private static final String DATABASE_TABLE = TABLE_NOTE2;
    private static DbHelper databaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelper = new DbHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TvShow> getAllTvshow() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setIdTvShow(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvShow.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                tvShow.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));
                tvShow.setTv_show_id(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                tvShow.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(LANGUANGE)));
                tvShow.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                tvShow.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));

                arrayList.add(tvShow);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTvshow(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(TITLE, tvShow.getName());
        args.put(DESCRIPTION, tvShow.getOverview());
        args.put(BACKDROP, tvShow.getBackdrop_path());
        args.put(IMG, tvShow.getPoster_path());
        args.put(ID, tvShow.getTv_show_id());
        args.put(LANGUANGE, tvShow.getOriginal_language());
        args.put(POPULARITY, tvShow.getPopularity());
        args.put(VOTE, tvShow.getVote_average());
        args.put(_ID, tvShow.getIdTvShow());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTvshow(String id) {
        return database.delete(TABLE_NOTE2, TITLE + " = '" + id + "'", null);
    }

}
