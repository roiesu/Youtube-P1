package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;
import com.example.android_client.repositories.LikeRepository;
import com.example.android_client.repositories.UserRepository;

public class LikeViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLiked;
    private MutableLiveData<Integer> videoLikes;
    private LikeRepository repository;

    public LikeViewModel(String userId, String videoId, LifecycleOwner owner) {
        this.repository = new LikeRepository(userId, videoId, owner);
        isLiked = repository.getIsLiked();
        videoLikes = repository.getLikeNum();
    }

    public MutableLiveData<Boolean> getIsLiked() {
        if (isLiked == null) {
            return new MutableLiveData<>();
        }
        return isLiked;
    }

    public MutableLiveData<Integer> getVideoLikes() {
        if (videoLikes == null) {
            return new MutableLiveData<>();
        }
        return videoLikes;
    }

    public void like(String username, String videoId) {
        this.repository.like(username, videoId);
    }
    public void dislike(String username, String videoId) {
        this.repository.dislike(username, videoId);
    }
}
