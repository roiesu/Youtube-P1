package com.example.android_client.view_models;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.Video;
import com.example.android_client.repositories.VideoListRepository;
import com.example.android_client.repositories.VideoRepository;

import java.util.List;

public class VideoViewModel extends ViewModel {

    private MutableLiveData<Video> video;
    private VideoRepository repository;

    public VideoViewModel(String channel, String videoId) {
        this.repository = new VideoRepository(channel, videoId);
        video = repository.get();
    }

    public MutableLiveData<Video> getVideo() {
        if (video == null) {
            return new MutableLiveData<>();
        }
        return video;
    }
}