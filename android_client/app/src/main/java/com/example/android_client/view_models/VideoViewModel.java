package com.example.android_client.view_models;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.datatypes.VideoWithUserWithComments;
import com.example.android_client.repositories.VideoListRepository;
import com.example.android_client.repositories.VideoRepository;

import java.util.List;

public class VideoViewModel extends ViewModel {

    private MutableLiveData<VideoWithUserWithComments> video;
    private VideoRepository repository;

    public VideoViewModel(String channel, String videoId) {
        this.repository = new VideoRepository(channel, videoId);
        video = repository.get();
    }

    public MutableLiveData<VideoWithUserWithComments> getVideo() {
        if (video == null) {
            return new MutableLiveData<>();
        }
        return video;
    }
}