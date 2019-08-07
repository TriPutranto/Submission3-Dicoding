package com.example.utaputranto.thirdsubmission.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbnoteapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DbContract.TABLE_NOTE,
            DbContract.NoteColumns._ID,
            DbContract.NoteColumns.TITLE,
            DbContract.NoteColumns.DESCRIPTION,
            DbContract.NoteColumns.DATE,
            DbContract.NoteColumns.ID

    );

    private static final String SQL_CREATE_TABLE2 = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DbContract.TABLE_NOTE2,
            DbContract.NoteColumns2._ID,
            DbContract.NoteColumns2.TITLE,
            DbContract.NoteColumns2.DESCRIPTION,
            DbContract.NoteColumns2.DATE);

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE2);
        Log.e("SQL", "Created : " + SQL_CREATE_TABLE);
        Log.e("SQL", "Created : " + SQL_CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_NOTE2);
        onCreate(db);
    }
}
