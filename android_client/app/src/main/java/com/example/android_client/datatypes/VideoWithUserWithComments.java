package com.example.android_client.datatypes;

import androidx.room.Relation;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoWithUserWithComments extends VideoWithUser{
    @Relation(parentColumn = "_id", entityColumn = "videoId")
    private List<Comment> comments;
    public VideoWithUserWithComments(String _id, String name, String src, Integer likesNum, String thumbnail, long duration, long views, Date date, String description, ArrayList<String> tags, User uploader, List<Comment> comments) {
        super(_id, name, src, likesNum, thumbnail, duration, views, date, description, tags, uploader);
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
