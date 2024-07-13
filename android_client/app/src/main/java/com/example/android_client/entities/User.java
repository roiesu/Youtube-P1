package com.example.android_client.entities;

import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey()
    private String _id;
    // Unique?
    private String username;
    private String password;
    private String name;
    private String image;

    public User(String _id,String username, String password, String name, String image){
        this._id = _id;
        this.username=username;
        this.password=password;
        this.name=name;
        this.image=image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }
    public Uri getImageUri(){
        return Uri.parse(this.image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof User&& ((User)obj).getUsername().equals(username);
    }
    public String toString(){
        return "("+_id+") "+username+": "+name;
    }


}
