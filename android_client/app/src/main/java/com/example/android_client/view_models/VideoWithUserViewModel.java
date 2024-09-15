package com.example.android_client.view_models;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.repositories.VideoWithUserRepository;

public class VideoWithUserViewModel extends ViewModel {

    private MutableLiveData<VideoWithUser> video;
    private VideoWithUserRepository repository;

    public VideoWithUserViewModel(String channel, String videoId, LifecycleOwner owner) {
        this.repository = new VideoWithUserRepository(channel, videoId,owner);
        video = repository.get();
    }

    public void setVideo(VideoWithUser video) {
        this.video.setValue(video);
    }

    public MutableLiveData<VideoWithUser> getVideo() {
        if (video == null) {
            return new MutableLiveData<>();
        }
        return video;
    }
}