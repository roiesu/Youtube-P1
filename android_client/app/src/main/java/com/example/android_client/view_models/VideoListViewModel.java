package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.dao.VideoDao;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;
import com.example.android_client.repositories.VideoListRepository;
import com.example.android_client.repositories.VideosWithUsersListRepository;

import java.util.List;

public class VideoListViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videoList;
    private LiveData<List<VideoWithUser>> videoWithUserList;
    private VideoListRepository repository;
    private MutableLiveData<List<VideoWithUser>> userVideos;
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

    public VideoListViewModel(VideoListRepository repository) {
        this.repository = repository;
        this.userVideos = new MutableLiveData<>();
    }

    public LiveData<List<VideoWithUser>> getUserVideos(String userId) {
        loadUserVideos(userId); // Trigger data load
        return userVideos;
    }

    public void loadUserVideos(String userId) {
        repository.getVideosByUserId(userId, userVideos);
    }
    public void getVideosDetailsByUser() {
        this.repository.getVideosDetailsByUser();
    }

    public void refreshMyVideos(String newVideoId, String newVideoUploaderId) {
        repository.refreshMyVideos(newVideoId, newVideoUploaderId);
    }

}
