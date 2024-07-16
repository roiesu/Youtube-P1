package com.example.android_client.entities;

import java.util.Date;

public class Comment {
    private String _id;
    private Date date;
    private User user;
    private Video video;
    private String text;
    private boolean edited;

    public Comment(String _id, Date date, User user, Video video, String text ,Boolean edited){
        this._id = _id;
        this.date = date;
        this.user = user;
        this.video = video;
        this.text = text;
        this.edited = edited;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
