package com.example.android_client.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userId", "videoId"}, foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "_id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Video.class, parentColumns = "_id", childColumns = "videoId", onDelete = ForeignKey.CASCADE)})
public class Like {
    @NonNull
    private String userId;
    @NonNull
    private String videoId;

    public Like(String userId, String videoId) {
        this.userId = userId;
        this.videoId = videoId;
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
