package com.example.movies.favoritDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Favorit>> favorits;
    public MainViewModel(@NonNull Application application) {
        super(application);
        FavoritDatabase database = FavoritDatabase.getInstance(this.getApplication());
        favorits = database.daoAccess().fetchAllFav();
    }

    public LiveData<List<Favorit>> getFavorits() {
        return favorits;
    }
}
