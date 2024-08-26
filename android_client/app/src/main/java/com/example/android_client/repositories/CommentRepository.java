package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.CommentApi;

import com.example.android_client.dao.CommentDao;
import com.example.android_client.dao.VideoDao;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Comment;

public class CommentRepository {
    private CommentDao dao;
    private VideoDao videoDao;
    private CommentData commentData;
    private CommentApi api;
    private LifecycleOwner owner;

    public CommentRepository(LifecycleOwner owner) {
        api = new CommentApi();
        this.owner = owner;
        AppDB instance = AppDB.getInstance();
        dao = instance.commentDao();
        videoDao = instance.videoDao();
        commentData = new CommentData();
    }

    class CommentData extends MutableLiveData<Comment> {
        public CommentData() {
            super();
        }
    }

    public MutableLiveData<Comment> get() {
        return commentData;
    }

    public void deleteComment(Comment comment, String videoUploader) {
        commentData.observe(owner, data -> {
            if ((data != null) && (data.get_id() != null)&&(data.getUserId() == null)) {
                new Thread(() -> {
                    videoDao.updateComments(comment.getVideoId(),-1);
                    dao.deleteComment(comment.get_id());
                }).start();
            }
        });
        api.deleteComment(commentData, comment, videoUploader);
    }

    public void editComment(Comment comment, String text, String videoUploader) {
        commentData.observe(owner, data -> {
            if ((data != null) && (data.getText().equals(text))) {
                new Thread(() -> {
                    dao.editComment(comment.get_id(), text);
                }).start();
            }
        });
        api.editComment(commentData, comment, text, videoUploader);
    }
}
