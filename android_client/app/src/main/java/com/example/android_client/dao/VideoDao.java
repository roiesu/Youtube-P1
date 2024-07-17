package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.List;

import retrofit2.http.GET;

@Dao
public interface VideoDao {
    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration,likesNum FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' "+
            "ORDER BY views DESC " +
            "LIMIT 20")
    List<Video> searchVideos(String searchValue);

    @Query("DELETE from video")
    void deleteAll();
    @Query("SELECT * from video")
    List<Video> index();


    @Insert
    void insert(Video...videos);

}
