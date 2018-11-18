package com.thomas.gamebacklogmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameObjectDao {

    @Insert
    void insert(GameObject game);

    @Update
    void update(GameObject game);

    @Delete
    void delete(GameObject game);

    @Query("DELETE FROM game_table")
    void deleteAllGameObjects();

    @Query("SELECT * FROM game_table ORDER BY id DESC")
    LiveData<List<GameObject>> getAllGameObjects();
}
