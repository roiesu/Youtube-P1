package com.example.android_client.entities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Video {

    private int id;
    private String name;
    private String uploader;
    private String displayUploader;
    private String src;
    private ArrayList<String> likes;
    private long views;
    private Date date_time;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<Comment> comments;

    public Video(){

    }

    public Video(int id, String name, String uploader, String displayUploader, String src, ArrayList<String> likes, long views, Date dateTime, String description, ArrayList<String> tags, ArrayList<Comment> comments) {
        this.id = id;
        this.name=name;
        this.uploader = uploader;
        this.displayUploader = displayUploader;
        this.src = src;
        this.likes = likes;
        this.views = views;
        this.date_time = dateTime;
        this.description = description;
        this.tags = tags;
        this.comments=comments;
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

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VideoPreview toPreview(Context context){
        return new VideoPreview(id,name,displayUploader,date_time,views,src,context);
    }
    public void addView(){
        this.views++;
    }
    public void like(String user){
        for(int i=0;i<likes.size();i++){
            if(likes.get(i).equals(user)){
                likes.remove(i);
                return;
            }
        }
        likes.add(user);
    }
    public void addComment(String username,String displayUsername,String text){
        Comment comment = new Comment(new Date(),username,displayUsername,text,false);
        comments.add(0,comment);
    }
    public void editComment(String username,Date date,String newText){
        for(Comment comment: comments){
            if(comment.getDate_time().equals(date)&&comment.getUser().equals(username)){
                comment.setText(newText);
                comment.setEdited(false);
            }
        }
    }
    public void deleteComment(String username,Date date){
        for (int i=0;i<comments.size();i++){
            if(comments.get(i).getDate_time().equals(date)&&comments.get(i).getUser().equals(username)){
                comments.remove(i);
            }
        }
    }

    public String toString(){
        return getName();
    }
}
