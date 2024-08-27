package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.VideoApi;

import com.example.android_client.dao.VideoDao;
import com.example.android_client.DataManager;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;

import java.util.List;


public class VideoRepository {
    private VideoDao dao;
    private VideoData videoData;
    private VideoApi api;
    private LifecycleOwner owner;

    public VideoRepository(VideoDao dao) {
        this.dao = dao;
    }
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

    public void getVideoWithUser(String channel, String videoId) {
        MutableLiveData<Long> views = new MutableLiveData<>();
        api.getVideo(views, channel, videoId);
        views.observe(owner, data -> {
            new Thread(() -> {
                dao.increaseViews(data.longValue(), videoId);
                videoData.postValue(dao.getVideoWithUser(channel, videoId));
            }).start();
        });
    }

    public void getVideo(String channel, String videoId) {
        new Thread(() -> {
            Video video = dao.getVideo(channel, videoId);
            this.videoData.postValue(video);
        }).start();
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

    public void deleteVideo(String videoId) {
        videoData.observe(owner, data -> {
            if ((data != null) && (data.get_id() != null)) {
                new Thread(() -> {
                    dao.deleteVideo(videoId);
                }).start();
            }
        });
        api.deleteVideo(videoData, videoId, DataManager.getCurrentUsername());
    }
    public LiveData<List<VideoWithUser>> getVideosByUserId(String userId) {
        return dao.getVideoWithUserByUserId(userId);
    }


    public void editVideo(MutableLiveData<Boolean> finished, String oldId, String oldThumbnail) {
        videoData.observe(owner, data -> {
            Video video = data;
            data.set_id(oldId);
            data.setThumbnail(oldThumbnail);
            new Thread(() -> {
                dao.update(video);
                finished.postValue(true);
            }).start();
        });
        api.updateVideo(this.videoData);
    }

}
