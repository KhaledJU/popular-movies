package com.example.movies.favoritDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorit.class}, version = 1, exportSchema = false)
public abstract class FavoritDatabase extends RoomDatabase {
    private static final String LOG_TAG = FavoritDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "db_favorit";
    private static FavoritDatabase instance;

    public static FavoritDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoritDatabase.class, FavoritDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return instance;
    }

    public abstract DaoAccess daoAccess();
}
