package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideosWithUsersListRepository {
    private VideoDao dao;
    private VideoListData videoListData;
    private VideoApi api;
    private LifecycleOwner owner;

    public VideosWithUsersListRepository(LifecycleOwner owner) {
        api = new VideoApi();
        videoListData = new VideoListData();
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
        this.owner = owner;
    }

    class VideoListData extends MutableLiveData<List<VideoWithUser>> {
        public VideoListData() {
            super();
        }

        @Override
        public void onActive() {
            reload();
        }
    }

    public MutableLiveData<List<VideoWithUser>> getAll() {
        return videoListData;
    }

    public void reload() {
        searchVideo("");
    }

    public void searchVideo(String query) {
        MutableLiveData<List<VideoWithUser>> temp = new MutableLiveData<>();
        new Thread(() -> {
            temp.postValue(dao.topTenVideos(query));
        }).start();
        temp.observe(owner, list -> {
            if (list.size() == 10) {
                Video lastVideo = list.get(9);
                new Thread(() -> {
                    List<VideoWithUser> tempList = list;
                    tempList.addAll(dao.restTenVideos(query, lastVideo.getViews(), lastVideo.get_id()));
                    videoListData.postValue(tempList);
                }).start();
            } else {
                videoListData.postValue(list);
            }
        });
    }
}
