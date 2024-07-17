package com.example.android_client.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.Date;

public class VideoWithUser extends Video {
    @Embedded
    private User uploader;
    @Relation(
            parentColumn = "_id",
            entityColumn = "videoId"
    )

    private ArrayList<Comment> comments;
    public VideoWithUser(String _id, String name, User uploader, String src, String thumbnail, long duration, ArrayList<String> likes, long views, Date date, String description, ArrayList<String> tags,ArrayList<Comment> comments) {
        super(_id, name, null, src, thumbnail, duration, likes, views, date, description, tags);
        this.uploader=uploader;
        this.comments=comments;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
