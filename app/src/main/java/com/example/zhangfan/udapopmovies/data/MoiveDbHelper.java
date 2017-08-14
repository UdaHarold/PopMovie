package com.example.zhangfan.udapopmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.zhangfan.udapopmovies.data.MovieContract.*;

import static android.R.attr.version;

/**
 * Created by zhangfan on 2017/7/31.
 */

public class MoiveDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";

    private static final int DATABASE_VERSION = 7;

    public MoiveDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE =

                " CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +

                        MovieEntry._ID               + " INTEGER PRIMARY KEY, " +

                        MovieEntry.COLUMN_TITLE       + " TEXT NOT NULL, "                 +

                        MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"                  +

                        MovieEntry.COLUMN_POPULARITY   + " REAL NOT NULL, "                    +
                        MovieEntry.COLUMN_VOTE   + " REAL NOT NULL, "                    +

                        MovieEntry.COLUMN_RELEASE   + " TEXT NOT NULL, "                    +
                        MovieEntry.COLUMN_POSTER   + " BLOB NOT NULL, "                    +

                        MovieEntry.COLUMN_STAR   + " INTEGER DEFAULT 0, "                    +

                        MovieEntry.COLUMN_BACKDROP + " BLOB NOT NULL) ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
