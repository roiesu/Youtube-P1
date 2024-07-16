package com.example.android_client.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.api.VideoApi;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideoListRepository {
//    private UserDao dao;
    private VideoListData videoListData;
    private VideoApi api;
    public VideoListRepository(){
        api = new VideoApi();
        videoListData = new VideoListData();
    }
    class VideoListData extends MutableLiveData<List<Video>> {
        public VideoListData(){
            super();
            api.getAll(this,"");
        }
    }
    public MutableLiveData<List<Video>> getAll(){
        return videoListData;
    }
    public void reload(){
        api.getAll(videoListData,"");
    }
    public void searchVideo(String query){
        api.getAll(videoListData,query);
    }
}
