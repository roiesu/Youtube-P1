package com.example.android_client.datatypes;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.Date;

public class VideoWithUser {
    @Relation(parentColumn = "uploaderId", entityColumn = "_id", projection = {"username", "name", "image"})
    private User uploader;
    @Embedded
    private Video video;

    public VideoWithUser(Video video, User uploader) {
        this.video = video;
        this.uploader = uploader;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
