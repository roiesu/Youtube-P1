package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.Video;
import com.example.android_client.repositories.VideoListRepository;

import java.util.List;

public class VideoListViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videoList;
    private VideoListRepository repository;

    public VideoListViewModel(LifecycleOwner owner) {
        this.repository = new VideoListRepository(owner);
        videoList = repository.getAll();
    }

    public MutableLiveData<List<Video>> getVideos() {
        if (videoList == null) {
            return new MutableLiveData<>();
        }
        return videoList;
    }

    // get video for user - channel page
    public void loadVideosForUser(String userId) {
        MutableLiveData<List<Video>> userVideos = new MutableLiveData<>();
        // FIX LATER FOR CHANNEL
//        this.repository.fetchVideosByUser(userId, userVideos);
        this.videoList = userVideos;
    }

    public void getVideosDetailsByUser() {
        this.repository.getVideosDetailsByUser();
    }

    public void addToMyVideos(String newVideoId, String newVideoUploaderId) {
        repository.addToMyVideos(newVideoId, newVideoUploaderId);
    }

}
