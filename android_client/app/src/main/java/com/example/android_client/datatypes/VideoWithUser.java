package com.example.android_client.datatypes;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.Date;

public class VideoWithUser extends Video {
    private User uploader;
    @Relation(
            parentColumn = "_id",
            entityColumn = "videoId"
    )

    private ArrayList<Comment> comments;

    public VideoWithUser(String _id, String name, User uploader, String src, String thumbnail, Integer likesNum, long duration, long views, Date date, String description, ArrayList<String> tags) {
        super(_id, name, null, src, likesNum, thumbnail, duration, views, date, description, tags);
        this.uploader = uploader;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
}
