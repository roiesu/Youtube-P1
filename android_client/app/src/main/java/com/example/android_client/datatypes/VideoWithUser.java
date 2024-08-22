package com.example.android_client.datatypes;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.Date;

public class VideoWithUser extends Video {

    @Relation(parentColumn = "uploaderId", entityColumn = "_id", projection = {"_id", "username", "name", "image"})
    private User uploader;

    public VideoWithUser(String _id, String name, String src, Integer likesNum, String thumbnail, long duration, long views, Date date, String description, ArrayList<String> tags, Integer commentsNum, User uploader) {
        super(_id, name, null, src, likesNum, thumbnail, duration, views, date, description, tags,commentsNum);
        this.uploader = uploader;
    }


    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }


}
