package com.example.android_client.datatypes;

import androidx.annotation.NonNull;
import androidx.room.Relation;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;

import java.util.Date;

public class CommentWithUser extends Comment {
    @Relation(parentColumn = "userId", entityColumn = "_id", projection = {"username", "name", "image"})
    private User user;

    public CommentWithUser(@NonNull String _id, Date date, @NonNull String userId, @NonNull String videoId, String text, Boolean edited, User user) {
        super(_id, date, userId, videoId, text, edited);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
