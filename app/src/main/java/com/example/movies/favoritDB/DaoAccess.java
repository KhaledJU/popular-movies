package com.example.movies.favoritDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFav(Favorit favorit);

    @Query("SELECT * FROM Favorit")
    LiveData<List<Favorit>> fetchAllFav();

    @Query("SELECT * FROM Favorit WHERE id =:favId")
    LiveData<Favorit> getFav(int favId);

    @Delete
    void deleteFav(Favorit favorit);
}
