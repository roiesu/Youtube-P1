package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.repositories.DataRepository;

import java.util.List;

public class DatabaseViewModel extends ViewModel {
    public DataRepository repository;
    private MutableLiveData<List<User>> users;
    private MutableLiveData<List<Video>> videos;
    private MutableLiveData<List<Comment>> comments;
    private MutableLiveData<Boolean> initialized;

    public DatabaseViewModel(){
        this.repository = new DataRepository();
        users = repository.getUsers();
        videos = repository.getVideos();
        comments = repository.getComments();
        initialized = new MutableLiveData<>();
        initialized.setValue(false);
    }
    public MutableLiveData<List<User>> getUsers(){
        if(users==null){
            return new MutableLiveData<>();
        }
        return users;
    }
    public MutableLiveData<List<Video>> getVideos(){
        if(videos==null){
            return new MutableLiveData<>();
        }
        return videos;
    }
    public MutableLiveData<List<Comment>> getComments(){
        if(comments==null){
            return new MutableLiveData<>();
        }
        return comments;
    }
    public MutableLiveData<Boolean> getInitialized(){
        if(initialized == null){
            return new MutableLiveData<>();
        }
        return initialized;
    }
    public void init(LifecycleOwner lifecycleOwner){
        this.repository.init(lifecycleOwner,initialized);
    }

}
