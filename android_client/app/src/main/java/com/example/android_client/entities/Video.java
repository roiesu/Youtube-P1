package com.example.android_client.entities;

import java.util.ArrayList;
import java.util.Date;

public class Video {

    private int id;
    private String uploader;
    private String displayUploader;
    private String src;
    private ArrayList<String> likes;
    private int views;
    private Date date_time;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<Comment> comments;

    public Video(int id, String uploader, String displayUploader, String src, ArrayList<String> likes, int views, Date dateTime, String description, ArrayList<String> tags) {
        this.id = id;
        this.uploader = uploader;
        this.displayUploader = displayUploader;
        this.src = src;
        this.likes = likes;
        this.views = views;
        date_time = dateTime;
        this.date_time = dateTime;
        this.description = description;
        this.tags = tags;
    }


    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayUploader() {
        return displayUploader;
    }

    public void setDisplayUploader(String displayUploader) {
        this.displayUploader = displayUploader;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
