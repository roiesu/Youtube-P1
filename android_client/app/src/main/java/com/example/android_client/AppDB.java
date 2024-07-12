package com.example.android_client;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDB extends RoomDatabase{
    public abstract UserDao userDao();
}
