package com.example.android_client.repositories;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.VideoDao;
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

        @Override
        public void onActive() {
            reload();
        }
    }

    public MutableLiveData<List<Video>> getAll() {
        return videoListData;
    }

    public void reload() {
        searchVideo("");
    }

    public void fetchVideosByUser(String userId, MutableLiveData<List<Video>> videoListData) {
        api.getVideosByUser(userId, videoListData);
    }

    public void searchVideo(String query) {
        MutableLiveData<List<Video>> temp = new MutableLiveData<>();
        new Thread(() -> {
            temp.postValue(dao.topTenVideos(query));
        }).start();
        temp.observe(owner, list -> {
            if (list.size() == 10) {
                Video lastVideo = list.get(9);
                new Thread(() -> {
                    List<Video> tempList = list;
                    tempList.addAll(dao.restTenVideos(query, lastVideo.getViews(), lastVideo.get_id()));
                    videoListData.postValue(tempList);
                }).start();
            } else {
                videoListData.postValue(list);
            }
        });
    }
}
