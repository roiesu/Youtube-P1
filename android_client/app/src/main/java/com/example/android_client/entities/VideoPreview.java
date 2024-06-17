package com.example.android_client.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import java.util.Date;

public class VideoPreview {
    private int id;
    private String name;
    private String displayUploader;
    private Date date;
    private long views;
    private Bitmap thumbnail;
    public VideoPreview(int id,String name, String displayUploader, Date date, long views, String src,Context context){
        this.id=id;
        this.name=name;
        this.displayUploader=displayUploader;
        this.date=date;
        this.views=views;
        this.thumbnail=createVideoThumb(context,src);
    }
    private Bitmap createVideoThumb(Context context,String src) {
        int videoResId = context.getResources().getIdentifier(src, "raw", context.getPackageName());
        String uriString = "android.resource://" + context.getPackageName() + "/" + videoResId;
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(context, Uri.parse(uriString));
            return mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception ex) {
            Log.w("BishBash",ex.toString());
        }
        return null;

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

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
