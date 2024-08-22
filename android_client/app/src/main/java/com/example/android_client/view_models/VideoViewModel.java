package com.example.android_client.view_models;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.datatypes.VideoWithUserWithComments;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;
import com.example.android_client.repositories.VideoRepository;

public class VideoViewModel extends ViewModel {

    private MutableLiveData<VideoWithUser> video;
    private VideoRepository repository;

    public VideoViewModel(String channel, String videoId, LifecycleOwner owner) {
        this.repository = new VideoRepository(channel, videoId,owner);
        video = repository.get();
    }
    public VideoViewModel(LifecycleOwner owner) {
        this.repository = new VideoRepository(owner);
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
    public void uploadVideo(){
        this.repository.upload();
    }
}