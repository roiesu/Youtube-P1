package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.List;

public class VideoListRepository {
    private VideoDao dao;
    private VideoListData videoListData;
    private VideoApi api;
    public VideoListRepository(){
        api = new VideoApi();
        videoListData = new VideoListData();
        AppDB instance = AppDB.getInstance();
        dao = instance.videoDao();
    }
    class VideoListData extends MutableLiveData<List<Video>> {
        public VideoListData(){
            super();
            api.getVideos(this,"");
        }
    }
    public MutableLiveData<List<Video>> getAll(){
        return videoListData;
    }
    public void reload(){
        api.getVideos(videoListData,"");
    }

    public void fetchVideosByUser(String userId, MutableLiveData<List<Video>> videoListData) {
        api.getVideosByUser(userId, videoListData);
    }
    public void searchVideo(String query){
        api.getVideos(videoListData,query);
    }

    public void init(LifecycleOwner lifecycleOwner){
        api.getAll(this.videoListData);
        videoListData.observe(lifecycleOwner,list->{
            new Thread(()->{
                dao.deleteAll();
                Video[] videoArray = list.toArray(new Video[0]);
                dao.insert(videoArray);
            }).start();
        });
    }
}
