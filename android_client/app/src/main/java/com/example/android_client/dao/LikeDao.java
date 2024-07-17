package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_client.entities.Like;

import java.util.List;
@Dao
public interface LikeDao {

    @Query("DELETE from `Like`")
    void deleteAll();

    @Insert
    void insert(Like...likes);

    @Query("SELECT * FROM `Like`")
    List<Like> index();
}
