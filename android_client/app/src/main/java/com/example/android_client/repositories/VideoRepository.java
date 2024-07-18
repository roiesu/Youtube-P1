package com.example.android_client.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.LikeDao;
import com.example.android_client.dao.UserDao;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.datatypes.VideoWithUserWithComments;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideoRepository {
    private VideoDao dao;
    private VideoData videoData;
    private VideoApi api;

    public VideoRepository(String channel, String videoId) {
        api = new VideoApi();
        videoData = new VideoData(channel, videoId);
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
    }

    class VideoData extends MutableLiveData<VideoWithUserWithComments> {
        public VideoData(String channel, String videoId) {
            super();
            getVideo(channel, videoId);
        }
    }

    public MutableLiveData<VideoWithUserWithComments> get() {
        return videoData;
    }

    public void getVideo(String channel, String videoId) {
        new Thread(() -> {
            VideoWithUserWithComments test = dao.getVideo(channel, videoId);
            int y = 5;
            videoData.postValue(test);
        }).start();
    }


}
