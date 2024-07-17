package com.example.android_client.repositories;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.CommentApi;
import com.example.android_client.api.UserApi;
import com.example.android_client.api.VideoApi;
import com.example.android_client.dao.CommentDao;
import com.example.android_client.dao.LikeDao;
import com.example.android_client.dao.UserDao;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.datatypes.VideoWithLikes;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.Like;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private UserDao userDao;
    private CommentDao commentDao;
    private VideoDao videoDao;
    private UserApi userApi;
    private LikeDao likeDao;
    private VideoApi videoApi;
    private CommentApi commentApi;
    private VideoListData videoListData;
    private UserListData userListData;
    private CommentListData commentListData;

    public DataRepository() {
        AppDB instance = AppDB.getInstance();
        this.userDao = instance.userDao();
        this.commentDao = instance.commentDao();
        this.videoDao = instance.videoDao();
        this.likeDao = instance.likeDao();
        userApi = new UserApi();
        videoApi = new VideoApi();
        commentApi = new CommentApi();
        videoListData = new VideoListData();
        userListData = new UserListData();
        commentListData = new CommentListData();
    }

    class VideoListData extends MutableLiveData<List<VideoWithLikes>> {
        public VideoListData() {
            super();
        }
    }

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();
        }
    }

    class CommentListData extends MutableLiveData<List<Comment>> {
        public CommentListData() {
            super();
        }
    }

    public MutableLiveData<List<User>> getUsers() {
        return userListData;
    }

    public MutableLiveData<List<Comment>> getComments() {
        return commentListData;
    }

    public MutableLiveData<List<VideoWithLikes>> getVideos() {
        return videoListData;
    }

    public void init(LifecycleOwner lifecycleOwner, MutableLiveData initialized) {
        Thread deleteThread = new Thread(() -> {
            try {
                userDao.deleteAll();
            } catch (Exception ex) {
                Log.w("THREAD ERROR", ex);
                Thread.currentThread().interrupt();
            }
        });
        try {
            deleteThread.start();
            deleteThread.join();
            userApi.getAll(userListData);
            userListData.observe(lifecycleOwner, usersList -> {
                videoApi.getAll(videoListData);
                videoListData.observe(lifecycleOwner, videoList -> {
                    commentApi.getAll(commentListData);
                    commentListData.observe(lifecycleOwner, commentsList -> {
                        Thread userThread = new Thread(() -> {
                            User[] userArray = usersList.toArray(new User[0]);
                            userDao.insert(userArray);
                        });
                        Thread videoThread = new Thread(() -> {
                            VideoWithLikes[] videoLikesArray = videoList.toArray(new VideoWithLikes[0]);
                            Video[] videoArray = new Video[videoList.size()];
                            for (int i = 0; i < videoArray.length; i++) {
                                videoArray[i] = videoLikesArray[i].getVideo();
                            }
                            videoDao.insert(videoArray);
                        });
                        Thread commentThread = new Thread(() -> {
                            Comment[] commentsArray = commentsList.toArray(new Comment[0]);
                            List<Like> likesArrayList = new ArrayList<>();
                            for (VideoWithLikes videoWithLikes : videoList) {
                                String videoId = videoWithLikes.getVideo().get_id();
                                for (String userId : videoWithLikes.getLikes()) {
                                    likesArrayList.add(new Like(userId, videoId));
                                }
                            }
                            Like[] likesArray = likesArrayList.toArray(new Like[0]);
                            likeDao.insert(likesArray);
                            commentDao.insert(commentsArray);
                        });
                        try {
                            userThread.start();
                            userThread.join();
                            videoThread.start();
                            videoThread.join();
                            commentThread.start();
                            commentThread.join();
                            initialized.setValue(true);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
            });

        } catch (InterruptedException ex) {
            Log.w("THREAD ERROR", ex);
            Thread.currentThread().interrupt();
        }

    }

}