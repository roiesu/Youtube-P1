package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.CommentApi;
import com.example.android_client.dao.CommentDao;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;

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

    class CommentListData extends MutableLiveData<List<Comment>> {
        public CommentListData() {
            super();
        }
//        @Override
//        protected void onActive(){
//            super.onActive();
//            new Thread(()->{
//               userData.postValue(dao.get(username));
//            }).start();
//        }

    }

    public MutableLiveData<List<Comment>> get() {
        return commentListData;
    }

    public void init(LifecycleOwner lifecycleOwner) {
        api.getAll(commentListData);
        commentListData.observe(lifecycleOwner, list -> {
            new Thread(() -> {
                dao.deleteAll();
                Comment [] commentsArray = list.toArray(new Comment[0]);
                dao.insert(commentsArray);
                List<Comment> comments = dao.index();
                int x=5;
            }).start();

        });
    }
}
