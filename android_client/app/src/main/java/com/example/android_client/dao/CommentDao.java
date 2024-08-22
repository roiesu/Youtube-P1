package com.example.android_client.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.android_client.datatypes.CommentWithUser;
import com.example.android_client.entities.Comment;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    List<Comment> index();

    @Query("DELETE FROM comment")
    void deleteAll();
    @Insert
    void insert(Comment ...comment);


    @Transaction
    @Query("SELECT * FROM comment WHERE videoId = :videoId")
    List<CommentWithUser> getCommentsByVideo(String videoId);
}
