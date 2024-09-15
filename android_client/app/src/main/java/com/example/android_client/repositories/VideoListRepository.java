package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.DataManager;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideoListRepository {
    private VideoDao dao;
    private VideoListData videoListData;
    private VideoApi api;
    private LifecycleOwner owner;

    public VideoListRepository(LifecycleOwner owner) {
        api = new VideoApi();
        videoListData = new VideoListData();
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
        this.owner = owner;
    }

    class VideoListData extends MutableLiveData<List<Video>> {
        public VideoListData() {
            super();
        }

    }

    public MutableLiveData<List<Video>> getAll() {
        return videoListData;
    }


    public void getVideosDetailsByUser() {
        api.getVideosDetailsByUser(videoListData, DataManager.getCurrentUsername());
    }

    public void addToMyVideos(String newVideoId, String newVideoUploaderId) {
        new Thread(() -> {
            Video newVideo = dao.getVideo(newVideoUploaderId, newVideoId);
            List<Video> temp = this.videoListData.getValue();
            temp.add(newVideo);
            this.videoListData.postValue(temp);
        }).start();
    }
  
    public void getVideosByUserId(String userId, MutableLiveData<List<VideoWithUser>> liveData) {
        new Thread(() -> {
            List<VideoWithUser> videos =dao.getVideosByUserId(userId);;
            liveData.postValue(videos);
        }).start();
    }
}

