package com.example.utaputranto.thirdsubmission.database;

import android.database.Cursor;
import android.provider.BaseColumns;

public class DbContract {

    public static final String AUTHORITY = "com.example.utaputranto.mynotesapp";
    public static final String SCHEME = "content";

    private DbContract(){}
    public static String TABLE_NOTE = "note";
    public static String TABLE_NOTE2 = "note2";


    static final  class NoteColumns implements BaseColumns{
        public static String TITLE = "title";
        public  static String DESCRIPTION = "description";
        public  static String DATE = "date";
        public  static String ID = "id";
    }

    static final  class NoteColumns2 implements BaseColumns{
        public static String TITLE = "title";
        public  static String DESCRIPTION = "description";
        public  static String DATE = "date";
    }


    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
