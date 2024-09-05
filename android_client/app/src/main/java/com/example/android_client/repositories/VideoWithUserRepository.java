package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;

import com.example.android_client.dao.VideoDao;
import com.example.android_client.datatypes.VideoWithUser;


public class VideoWithUserRepository {
    private VideoDao dao;
    private VideoData videoData;
    private VideoApi api;
    private LifecycleOwner owner;

    public VideoWithUserRepository(String channel, String videoId, LifecycleOwner owner) {
        api = new VideoApi();
        this.owner = owner;
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
        videoData = new VideoData(channel, videoId);
    }

    class VideoData extends MutableLiveData<VideoWithUser> {
        public VideoData(String channel, String videoId) {
            super();
            getVideo(channel, videoId);
        }
    }

    public MutableLiveData<VideoWithUser> get() {
        return videoData;
    }

    public void getVideo(String channel, String videoId) {
        MutableLiveData<Long> views = new MutableLiveData<>();
        api.getVideo(views, channel, videoId);
        views.observe(owner, data -> {
            new Thread(() -> {
                if(data==-1){
                    videoData.postValue(null);
                }
                else {
                    dao.increaseViews(data.longValue(), videoId);
                    videoData.postValue(dao.getVideoWithUser(channel, videoId));
                }
            }).start();
        });

    }


}
