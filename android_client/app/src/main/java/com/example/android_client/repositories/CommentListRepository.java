package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.CommentApi;
import com.example.android_client.dao.CommentDao;
import com.example.android_client.datatypes.CommentWithUser;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentListRepository {
    private CommentDao dao;
    private CommentListData commentListData;
    private CommentApi api;

    public CommentListRepository() {
        api = new CommentApi();
        commentListData = new CommentListData();
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.commentDao();
    }

    class CommentListData extends MutableLiveData<List<CommentWithUser>> {
        public CommentListData() {
            super();
        }
    }

    public MutableLiveData<List<CommentWithUser>> get() {
        return commentListData;
    }

    public void init(LifecycleOwner lifecycleOwner) {
        api.getAll(commentListData);
        commentListData.observe(lifecycleOwner, list -> {
            new Thread(() -> {
                Comment [] commentsArray = list.toArray(new Comment[0]);
                dao.insert(commentsArray);
            }).start();
        });
    }
    public void getCommentsByVideo(String videoId){
        new Thread(()->{
            List<CommentWithUser> list = dao.getCommentsByVideo(videoId);
           commentListData.postValue(list);
        }).start();
    }
}
