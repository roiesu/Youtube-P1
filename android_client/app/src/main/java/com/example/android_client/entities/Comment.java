package com.example.android_client.entities;

import java.util.Date;

public class Comment {
    private Date date_time;
    private String user;
    private String display_name;
    private String text;

    public Comment(Date dateTime, String user, String displayName, String text) {
        date_time = dateTime;
        this.user = user;
        display_name = displayName;
        this.text = text;
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
}
