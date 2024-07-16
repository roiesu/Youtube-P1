package com.example.android_client.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.Video;
import com.example.android_client.repositories.VideoListRepository;

import java.util.List;

public class VideoListViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videoList;
    private VideoListRepository repository;
    public VideoListViewModel(){
        this.repository = new VideoListRepository();
        videoList = repository.getAll();
    }
    public MutableLiveData<List<Video>> getVideos(){
        if(videoList==null){
            return new MutableLiveData<>();
        }
        return videoList;
    }
    public void searchVideo(String query){
        this.repository.searchVideo(query);
    }
    public void reload(){
        this.repository.reload();
    }

// get video for user - channel page
    public void loadVideosForUser(String userId) {
        MutableLiveData<List<Video>> userVideos = new MutableLiveData<>();
        this.repository.fetchVideosByUser(userId, userVideos);
        this.videoList = userVideos;
    }
}
