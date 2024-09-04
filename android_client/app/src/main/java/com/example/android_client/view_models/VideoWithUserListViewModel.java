package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.repositories.VideosWithUsersListRepository;

import java.util.List;

public class VideoWithUserListViewModel extends ViewModel {

    private MutableLiveData<List<VideoWithUser>> videoList;
    private VideosWithUsersListRepository repository;

    public VideoWithUserListViewModel(LifecycleOwner owner) {
        this.repository = new VideosWithUsersListRepository(owner);
        videoList = repository.getAll();
    }

    public MutableLiveData<List<VideoWithUser>> getVideos() {
        if (videoList == null) {
            return new MutableLiveData<>();
        }
        return videoList;
    }

    public void searchVideo(String query) {
        this.repository.searchVideo(query);
    }

    public void getRecommendations(String userId,String videoId){
        this.repository.getRecommendations(userId,videoId);
    }

    public void reload() {
        this.repository.reload();
    }

}
