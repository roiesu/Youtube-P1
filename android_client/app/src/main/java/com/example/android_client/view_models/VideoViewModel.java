package com.example.android_client.view_models;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;
import com.example.android_client.repositories.VideoRepository;

public class VideoViewModel extends ViewModel {

    private MutableLiveData<Video> video;
    private VideoRepository repository;

    public VideoViewModel(LifecycleOwner owner) {
        this.repository = new VideoRepository(owner);
        video = repository.get();
    }

    public void setVideo(Video video) {
        this.video.setValue(video);
    }

    public MutableLiveData<Video> getVideo() {
        if (video == null) {
            return new MutableLiveData<>();
        }
        return video;
    }

    public void uploadVideo(MutableLiveData finished) {
        this.repository.upload(finished);
    }

    public void deleteVideo(String id) {
        this.repository.deleteVideo(id);
    }
}