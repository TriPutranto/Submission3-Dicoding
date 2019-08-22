package com.example.utaputranto.thirdsubmission.database;

import android.provider.BaseColumns;

public class DbContract {

    private DbContract(){}
    public static String TABLE_NOTE = "note";
    public static String TABLE_NOTE2 = "note2";


    static final  class NoteColumns implements BaseColumns{
        public static String TITLE = "title";
        public  static String DESCRIPTION = "description";
        public  static String DATE = "date";
        public  static String ID = "id";
        public  static String IMG = "image";
        public  static String BACKDROP = "backrop";
        public  static String VOTE = "vote";
        public  static String POPULARITY = "popularity";
        public  static String LANGUANGE = "language";
    }

    static final  class NoteColumns2 implements BaseColumns{
        public static String TITLE = "title";
        public  static String IMG = "image";
        public  static String ID = "id";
        public  static String LANGUANGE = "language";
        public  static String VOTE = "vote";
        public  static String POPULARITY = "popular";
        public  static String DESCRIPTION = "description";
        public  static String BACKDROP = "backdrop";
    }
}
