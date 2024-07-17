package com.example.android_client.datatypes;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoWithUserAndComments extends VideoWithUser {
    private List<Comment> comments;

    public VideoWithUserAndComments(String _id, String name, User uploader, String src, String thumbnail, Integer likesNum, long duration, long views, Date date, String description, ArrayList<String> tags, List<Comment> comments) {
        super(_id, name, uploader, src, thumbnail, likesNum, duration, views, date, description, tags);
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
