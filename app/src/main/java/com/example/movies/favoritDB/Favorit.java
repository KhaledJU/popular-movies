package com.example.movies.favoritDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Favorit implements Serializable {
    @PrimaryKey(autoGenerate = false)
    private int id;

    public Favorit(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
