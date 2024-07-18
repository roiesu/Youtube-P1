package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.LikeApi;
import com.example.android_client.dao.LikeDao;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.entities.Like;


public class LikeRepository {
    private LikeDao likeDao;
    private VideoDao videoDao;
    private LikeData isLiked;
    private LikeNumData likeNum;
    private LikeApi api;

    private LifecycleOwner owner;

    public LikeRepository(String userId, String videoId, LifecycleOwner owner, Integer videoLikesNum) {
        api = new LikeApi();
        isLiked = new LikeData(userId, videoId);
        likeNum = new LikeNumData(videoLikesNum);
        // Room
        AppDB database = AppDB.getInstance();
        likeDao = database.likeDao();
        videoDao = database.videoDao();
        this.owner = owner;
    }

    class LikeData extends MutableLiveData<Boolean> {
        public LikeData(String userId, String videoId) {
            super();
            isLiked(userId, videoId);
        }

    }

    class LikeNumData extends MutableLiveData<Integer> {
        public LikeNumData(Integer videoLikesNum) {
            super();
            setValue(videoLikesNum);
        }

    }

    public LikeData getIsLiked() {
        return isLiked;
    }

    public LikeNumData getLikeNum() {
        return likeNum;
    }

    public void isLiked(String userId, String videoId) {
        new Thread(() -> {
            Like like = likeDao.getLike(userId, videoId);
            isLiked.postValue(like != null);
        }).start();
    }

    public void like(String username, String videoId) {
        MutableLiveData<Like> temp = new MutableLiveData<>();
        api.likeVideo(username, videoId, temp);
        temp.observe(owner, likeValue -> {
            if (likeValue != null) {
                new Thread(() -> {
                    likeDao.insert(likeValue);
                    isLiked.postValue(true);
                    videoDao.setLikesNum(likeNum.getValue() + 1, videoId);
                    likeNum.postValue(likeNum.getValue() + 1);
                }).start();
            }
        });
    }
}
