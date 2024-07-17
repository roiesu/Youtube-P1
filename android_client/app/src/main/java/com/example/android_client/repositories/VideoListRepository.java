package com.example.android_client.repositories;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideoListRepository {
    private VideoDao dao;
    private VideoListData videoListData;
    private VideoApi api;

    public VideoListRepository() {
        api = new VideoApi();
        videoListData = new VideoListData();
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
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
        new Thread(() -> {
            videoListData.postValue(dao.topTenVideos(""));
        }).start();
    }

    public void fetchVideosByUser(String userId, MutableLiveData<List<Video>> videoListData) {
        api.getVideosByUser(userId, videoListData);
    }

    public void searchVideo(String query) {
        Thread first = new Thread(() -> {
            videoListData.postValue(dao.topTenVideos(query));
        });
        Thread second = new Thread(() -> {
            List<Video> videoList = videoListData.getValue();
            if (videoList.size() > 10) {
                Long maxViews = videoList.get(9).getViews();
                videoList.addAll(dao.restTenVideos(query, maxViews));
                videoListData.postValue(videoList);
            }
        });
        try {
            first.start();
            first.join();
            second.start();
            second.join();
        } catch (Exception ex) {
            Log.w("ERROR", ex);
        }

    }

    public void init(LifecycleOwner lifecycleOwner) {
        api.getAll(this.videoListData);
        videoListData.observe(lifecycleOwner, list -> {
            new Thread(() -> {
                Video[] videoArray = list.toArray(new Video[0]);
                dao.insert(videoArray);
                List<Video> videos = dao.index();
                int x = 5;
            }).start();
        });
    }
}
