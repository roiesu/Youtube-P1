package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_client.entities.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    List<Comment> index();

    @Query("DELETE FROM comment")
    void deleteAll();
    @Insert
    void insert(Comment ...comment);
}
