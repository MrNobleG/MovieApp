package com.example.movieapp.Activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION=1;
    private static String dataBaseName="FavoriteFilms";
    public FavDB(Context context) {
        super(context, dataBaseName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS FavoriteFilms ( imdbId TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
