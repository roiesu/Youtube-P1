package com.example.android_client.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;

import java.util.ArrayList;
import java.util.Date;

@Entity(
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "_id", childColumns = "uploaderId", onDelete = ForeignKey.CASCADE)
)
public class Video {
    @PrimaryKey
    @NonNull
    private String _id;
    private String name;
    private String uploaderId;
    private String src;
    private String thumbnail;
    private long duration;
    private ArrayList<String> likes;
    private long likesNum;
    private long views;
    private Date date;
    private String description;
    private ArrayList<String> tags;

//    private ArrayList<Comment> comments;
    public Video(){}
    public Video(String _id, String name, String uploaderId, String src, String thumbnail, long duration, ArrayList<String> likes, long views, Date date, String description, ArrayList<String> tags) {
        this._id = _id;
        this.name = name;
        this.uploaderId = uploaderId;
        this.src = src;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.likes = likes;
        this.likesNum=likes.size();
        this.views = views;
        this.date = date;
        this.description = description;
        this.tags = tags;
    }

    public Video(String _id, String name, String uploader, String thumbnail, long duration, long views, Date date) {
        this._id = _id;
        this.name = name;
        this.uploaderId = uploader;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.views = views;
        this.date = date;
    }

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

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploader) {
        this.uploaderId = uploader;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getThumbnailFromServer() {
        return ContextApplication.context.getString(R.string.BaseUrlMedia) + "image/" + thumbnail;
    }

    public String getVideoFromServer() {
        return ContextApplication.context.getString(R.string.BaseUrlMedia) + "video/" + src;
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

    public ArrayList<String> getLikes() {
        return this.likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
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

//    public ArrayList<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(ArrayList<Comment> comments) {
//        this.comments = comments;
//    }

    public long getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(long likesNum) {
        this.likesNum = likesNum;
    }
}
