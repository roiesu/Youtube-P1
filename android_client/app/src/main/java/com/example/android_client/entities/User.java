package com.example.android_client.entities;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity (tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private String _id;
    // Unique?
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")

    private String password;
    @ColumnInfo(name = "name")

    private String name;
    @ColumnInfo(name = "image")

    private String image;

    public User(String _id, String username, String password, String name, String image) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.image = image;
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

    public String getImageFromServer() {
        return ContextApplication.context.getString(R.string.BaseUrlMedia) + "image/" + image;
    }

    public String getImage() {
        return image;
    }

    public Uri getImageUri() {
        return Uri.parse(this.image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        _id = id;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof User && ((User) obj).getUsername().equals(username);
    }

    public String toString() {
        return "(" + _id + ") " + username + ": " + name;
    }


}
