package com.example.android_client.entities;

import java.util.Date;

public class Comment {
    private Date date_time;
    private String user;
    private String display_name;
    private String text;
    private boolean edited;

    public Comment(Date dateTime, String user, String displayName, String text,boolean edited) {
        this.date_time = dateTime;
        this.user = user;
        this.display_name = displayName;
        this.text = text;
        this.edited=edited;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void edit(String text){
        this.text=text;
        this.edited=true;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
