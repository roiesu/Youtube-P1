package com.example.android_client.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
@Entity
public class Video {
    @PrimaryKey @NonNull
    private String _id;
    private String name;
    private User uploader;
    private String src;
    private String thumbnail;
    private long duration;
    private ArrayList<String> likes;
    private long views;
    private Date date;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<Comment> comments;

    public Video() {}

    public Video(String _id, String name, User uploader, String src, String thumbnail, long duration, ArrayList<String> likes, long views, Date date, String description, ArrayList<String> tags, ArrayList<Comment> comments) {
        this._id = _id;
        this.name = name;
        this.uploader = uploader;
        this.src = src;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.likes = likes;
        this.views = views;
        this.date = date;
        this.description = description;
        this.tags = tags;
        this.comments = comments;
    }
    public Video(String _id, String name, User uploader, String thumbnail, long duration, long views, Date date){
        this._id = _id;
        this.name = name;
        this.uploader = uploader;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.views = views;
        this.date = date;
    }

//    public Video(int id, String name, User uploader, String src, String description, ArrayList<String> tags, Context context) {
//        this.id = id;
//        this.name = name;
//        this.uploader = uploader.getUsername();
//        this.displayUploader = uploader.getName();
//        this.src = src;
//        this.likes = new ArrayList<>();
//        this.views = 0;
//        this.date_time = new Date();
//        this.description = description;
//        this.tags = tags;
//        this.comments = new ArrayList<>();
//        createVideoDetails(context);
//    }

    public void createVideoDetails(Context context) {
        try {
            MediaMetadataRetriever mediaRetriever = new MediaMetadataRetriever();
            mediaRetriever.setDataSource(context, Uri.parse(getSrc()));
            String time = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long seconds = (long) Math.floor(Long.parseLong(time) / 1000);
            this.setDuration(seconds);
//            this.setThumbnail(mediaRetriever.getFrameAtTime());
            mediaRetriever.release();
        } catch (Exception ex) {
            Log.w("Error", ex.toString());
        }
    }


    public void like(String user) {
        for (int i = 0; i < likes.size(); i++) {
            if (likes.get(i).equals(user)) {
                likes.remove(i);
                return;
            }
        }
        likes.add(user);
    }

    public void addComment(String username, String displayUsername, String text) {
        Comment comment = new Comment(new Date(), username, displayUsername, text, false);
        comments.add(0, comment);
    }

    public void editComment(String username, Date date, String newText) {
        for (Comment comment : comments) {
            if (comment.getDate_time().equals(date) && comment.getUser().equals(username)) {
                comment.setText(newText);
                comment.setEdited(false);
            }
        }
    }

    public void deleteComment(String username, Date date) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getDate_time().equals(date) && comments.get(i).getUser().equals(username)) {
                comments.remove(i);
            }
        }
    }

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
