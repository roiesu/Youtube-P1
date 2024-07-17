package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_client.entities.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' "+
            "ORDER BY views DESC " +
            "LIMIT 10")
    List<Video> topTenVideos(String searchValue);

    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' AND views <= :maxViews "+
            "ORDER BY RANDOM() " +
            "LIMIT 10")
    List<Video> restTenVideos(String searchValue, Long maxViews);

    @Query("DELETE from video")
    void deleteAll();
    @Query("SELECT * from video")
    List<Video> index();


    @Insert
    void insert(Video...videos);

}
