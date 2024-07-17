package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.android_client.entities.User;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * from comment")
    List<User> index();
    @Query("SELECT _id,username,name,image from user WHERE username= :username")
    User get(String username);


}
