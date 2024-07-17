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
        }
        @Override
        public void onActive(){
            reload();
        }
    }
    public MutableLiveData<List<Video>> getAll(){
        return videoListData;
    }
    public void reload(){
        new Thread(()->{
            videoListData.postValue(dao.searchVideos(""));
        }).start();
    }

    public void fetchVideosByUser(String userId, MutableLiveData<List<Video>> videoListData) {
        api.getVideosByUser(userId, videoListData);
    }
    public void searchVideo(String query){
       new Thread(()->{
           videoListData.postValue(dao.searchVideos(query));
       }).start();
    }

    public void init(LifecycleOwner lifecycleOwner){
        api.getAll(this.videoListData);
        videoListData.observe(lifecycleOwner,list->{
            new Thread(()->{
                Video [] videoArray = list.toArray(new Video[0]);
                dao.insert(videoArray);
                List<Video> videos = dao.index();
                int x=5;
            }).start();
        });
    }
}
