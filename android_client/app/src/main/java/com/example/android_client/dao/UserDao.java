package com.example.android_client.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.android_client.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("DELETE from user")
    Void deleteAll();

    @Query("SELECT _id,username,name,image from user WHERE username= :username")
    User get(String username);
    @Query("SELECT *  from user WHERE _id=:id")
    LiveData<User> getUserById(String id);

    @Query("SELECT * from user")
    List<User> index();

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Transaction
    @Delete
    void delete(User... users);

}
