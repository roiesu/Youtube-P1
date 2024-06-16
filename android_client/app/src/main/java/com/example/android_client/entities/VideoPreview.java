package com.example.android_client.entities;

import java.util.Date;

public class VideoPreview {
    private int id;
    private String name;
    private String displayUploader;
    private Date date;
    private long views;
    private String thumbnail;
    public VideoPreview(int id,String name, String displayUploader, Date date, long views, String thumbnail){
        this.id=id;
        this.name=name;
        this.displayUploader=displayUploader;
        this.date=date;
        this.views=views;
        this.thumbnail=thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayUploader() {
        return displayUploader;
    }

    public void setDisplayUploader(String displayUploader) {
        this.displayUploader = displayUploader;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
