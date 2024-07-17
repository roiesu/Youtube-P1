package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_client.entities.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * from comment")
    List<Comment> index();

    @Query("DELETE from comment")
    void deleteAll();

    @Insert
    void insert(Comment ...comments);


}
