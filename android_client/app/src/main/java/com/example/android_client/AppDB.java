package com.example.android_client;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.android_client.dao.CommentDao;
import com.example.android_client.dao.LikeDao;
import com.example.android_client.dao.UserDao;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.Like;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

@Database(entities = {User.class, Comment.class, Video.class, Like.class},version = 13)
@TypeConverters({Converters.class})
public abstract class AppDB extends RoomDatabase{
    public static AppDB instance;
    public abstract UserDao userDao();
    public abstract VideoDao videoDao();
    public abstract CommentDao commentDao();
    public abstract LikeDao likeDao();

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
