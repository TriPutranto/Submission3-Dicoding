package com.example.utaputranto.thirdsubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.utaputranto.thirdsubmission.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.TABLE_TVSHOW;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.IMG;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.OVERVIEW;
import static com.example.utaputranto.thirdsubmission.db.DatabaseContract.CatalogColumns.TITLE;

public class TvShowFavHelper {
    private static String DATABASE_TABLE_TV = TABLE_TVSHOW;
    public Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TvShowFavHelper(Context context) {
        this.context = context;
    }

    public TvShowFavHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<TvShow> query() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_TV, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setIdTvShow(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tvShow.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));

                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(TvShow tvShow) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, tvShow.getName());
        initialValues.put(OVERVIEW, tvShow.getOverview());
        initialValues.put(IMG, tvShow.getPoster_path());
        return database.insert(DATABASE_TABLE_TV, null, initialValues);
    }

    public int update(TvShow tvShow) {
        ContentValues values = new ContentValues();
        values.put(TITLE, tvShow.getName());
        values.put(OVERVIEW, tvShow.getOverview());
        values.put(IMG, tvShow.getPoster_path());

        return database.update(DATABASE_TABLE_TV, values, _ID + "= '" + tvShow.getTv_show_id() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_TVSHOW, _ID + " = '" + id + "'", null);
    }
}
