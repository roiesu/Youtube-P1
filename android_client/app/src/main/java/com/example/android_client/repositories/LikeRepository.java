package com.example.android_client.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.LikeApi;
import com.example.android_client.dao.LikeDao;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Like;


public class LikeRepository {
    private LikeDao dao;
    private LikeData likeData;
    private LikeApi api;

    public LikeRepository(String userId, String videoId) {
        api = new LikeApi();
        likeData = new LikeData(userId, videoId);
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.likeDao();
    }

    class LikeData extends MutableLiveData<Boolean> {
        public LikeData(String userId, String videoId) {
            super();
            isLiked(userId, videoId);
        }

    }

    public LikeData get() {
        return likeData;
    }

    public void isLiked(String userId, String videoId) {
        new Thread(() -> {
            Like like = dao.getLike(userId, videoId);
            likeData.postValue(like != null);
        }).start();
    }
}
