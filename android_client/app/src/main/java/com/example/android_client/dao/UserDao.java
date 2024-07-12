package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_client.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT _id,username,name,password from user WHERE username= :username")
    User get(String username);
    @Query("SELECT _id,username,name,password from user")
    List<User> index();
    @Insert
    void insert(User...users);
    @Update
    void update(User ...users);

    @Delete
    void delete(User ...users);
}
