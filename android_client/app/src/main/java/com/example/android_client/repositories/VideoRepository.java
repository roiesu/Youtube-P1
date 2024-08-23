package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;

import com.example.android_client.dao.VideoDao;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;


public class VideoRepository {
    private VideoDao dao;
    private VideoData videoData;
    private VideoApi api;
    private LifecycleOwner owner;


    public VideoRepository(LifecycleOwner owner) {
        api = new VideoApi();
        this.owner = owner;
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
        videoData = new VideoData();
    }

    class VideoData extends MutableLiveData<Video> {
        public VideoData() {
            super();
        }
    }

    public MutableLiveData<Video> get() {
        return videoData;
    }

    public void getVideo(String channel, String videoId) {
        MutableLiveData<Long> views = new MutableLiveData<>();
        api.getVideo(views, channel, videoId);
        views.observe(owner, data -> {
            new Thread(() -> {
                dao.increaseViews(data.longValue(), videoId);
                videoData.postValue(dao.getVideo(channel, videoId));
            }).start();
        });

    }

    public void upload(MutableLiveData finished) {
        this.videoData.observe(owner, data -> {
            if (data != null && data.get_id() != null) {
                new Thread(() -> {
                    dao.insert(data);
                    finished.postValue(true);
                }).start();
            }
        });
        this.api.uploadVideo(videoData);
    }

}
