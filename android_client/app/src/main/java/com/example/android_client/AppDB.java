package com.example.android_client;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDB extends RoomDatabase{
    public static AppDB instance;
    public abstract UserDao userDao();

    public static synchronized AppDB getInstance(){
        if (instance == null) {
            instance = Room.databaseBuilder(ContextApplication.context.getApplicationContext(),
                            AppDB.class, "LocalData")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
