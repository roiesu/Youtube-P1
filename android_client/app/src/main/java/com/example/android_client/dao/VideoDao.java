package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Transaction
    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' " +
            "ORDER BY views DESC " +
            "LIMIT 10")
    List<VideoWithUser> topTenVideos(String searchValue);

    @Transaction
    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' AND views <= :lastViews AND _id <> :lastId " +
            "ORDER BY RANDOM() " +
            "LIMIT 10")
    List<VideoWithUser> restTenVideos(String searchValue, Long lastViews, String lastId);


    @Query("DELETE from video")
    void deleteAll();

    @Query("SELECT * from video")
    List<Video> index();


    @Insert
    void insert(Video... videos);

}
