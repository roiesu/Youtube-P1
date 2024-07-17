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
    @Query("DELETE from video")
    void deleteAll();
    @Query("SELECT * from video")
    List<Video> index();

    @Query("SELECT _id,username,name,image from user WHERE username= :username")
    User get(String username);

    @Insert
    void insert(Video...videos);

}
