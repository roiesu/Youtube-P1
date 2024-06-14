package com.example.android_client.entities;

public class User {
    private String username;
    private String password;
    private String name;
    private String image;

    public User(String username, String password, String name, String image){
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

    public void setImage(String image) {
        this.image = image;
    }
}