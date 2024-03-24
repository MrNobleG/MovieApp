package com.example.movieapp.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseActivity extends AppCompatActivity {
    private static final String TAG = "DatabaseAccess";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the IMDb ID and checkbox state from intent extras
        String imdbId = getIntent().getStringExtra("imdbId");
        boolean isChecked = getIntent().getBooleanExtra("isChecked", false);

        // Open the database
        FavDB favDB = new FavDB(this);
        db = favDB.getWritableDatabase();

        // Check the checkbox state and perform insert or delete operation accordingly
        if (isChecked) {
            insertFavorite(imdbId);
        } else {
            deleteFavorite(imdbId);
        }

        // Close the database
        db.close();

        // Finish the activity
        finish();
    }

    private void insertFavorite(String imdbId) {
        ContentValues values = new ContentValues();
        values.put("imdbId", imdbId);
        long result = db.insert("FavoriteFilms", null, values);
        if (result == -1) {
            Log.e(TAG, "Error inserting favorite: " + imdbId);
        } else {
            Log.d(TAG, "Favorite inserted successfully: " + imdbId);
        }
    }

    private void deleteFavorite(String imdbId) {
        int result = db.delete("FavoriteFilms", "imdbId = ?", new String[]{imdbId});
        if (result == 0) {
            Log.e(TAG, "Favorite not found for deletion: " + imdbId);
        } else {
            Log.d(TAG, "Favorite deleted successfully: " + imdbId);
        }
        db.close();
    }



}
