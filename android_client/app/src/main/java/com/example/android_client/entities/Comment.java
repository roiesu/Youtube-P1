package com.example.android_client.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "_id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Video.class, parentColumns = "_id", childColumns = "videoId", onDelete = ForeignKey.CASCADE)
})
public class Comment {
    @PrimaryKey
    @NonNull
    private String _id;
    private Date date;
    @NonNull
    private String userId;
    @NonNull
    private String videoId;
    private String text;
    private Boolean edited;

    public Comment(@NonNull String _id, Date date, @NonNull String userId, @NonNull String videoId, String text, Boolean edited) {
        this._id = _id;
        this.date = date;
        this.userId = userId;
        this.videoId = videoId;
        this.text = text;
        this.edited = edited;
    }
    public Comment(){

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String video) {
        this.videoId = video;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
