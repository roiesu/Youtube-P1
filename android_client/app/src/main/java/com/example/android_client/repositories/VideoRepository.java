package com.example.android_client.repositories;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.api.VideoApi;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideoRepository {
    //    private UserDao dao;
    private VideoData videoData;
    private VideoApi api;

    public VideoRepository(String channel, String videoId) {
        api = new VideoApi();
        videoData = new VideoData(channel, videoId);
    }

    class VideoData extends MutableLiveData<Video> {
        public VideoData(String channel, String videoId) {
            super();
            api.get(this, channel, videoId);
        }
    }

    public MutableLiveData<Video> get() {
        return videoData;
    }
}