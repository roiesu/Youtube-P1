package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_client.entities.Like;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface LikeDao {

    @Query("DELETE from `Like`")
    void deleteAll();

    @Insert
    void insert(Like... likes);

    @Delete
    void delete(Like... likes);

    @Query("SELECT * FROM `Like`")
    List<Like> index();

    @Query("SELECT * FROM `Like` WHERE userId = :userId AND videoId = :videoId")
    Like getLike(String userId, String videoId);

    @Query("SELECT COUNT(*) FROM `Like` WHERE videoId = :videoId")
    Integer countLikes(String videoId);
}
