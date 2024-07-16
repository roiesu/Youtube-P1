package com.example.android_client.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userID", "videoId"})
public class Like {
    private String userId;
    private String videoId;
    public Like(String userId, String videoId){
        this.userId=userId;
        this.videoId=videoId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
