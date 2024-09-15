package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.Comment;
import com.example.android_client.repositories.CommentRepository;

public class CommentViewModel extends ViewModel {

    private MutableLiveData<Comment> comment;
    private CommentRepository repository;

    public CommentViewModel(LifecycleOwner owner) {
        this.repository = new CommentRepository(owner);
        comment = repository.get();
    }

    public void setComment(Comment comment) {
        this.comment.setValue(comment);
    }

    public MutableLiveData<Comment> getComment() {
        if (comment == null) {
            return new MutableLiveData<>();
        }
        return comment;
    }
    public void editComment(Comment comment, String text, String videoUploader, MutableLiveData<Boolean> finished){
        this.repository.editComment(comment, text, videoUploader, finished);
    }

    public void deleteComment(Comment comment, String videoUploader, MutableLiveData<Boolean> finished) {
        this.repository.deleteComment(comment, videoUploader, finished);
    }

    public void addComment(String text, String videoUploader, String videoId, MutableLiveData<Boolean> finished){
        this.repository.addComment(text, videoUploader, videoId, finished);
    }
}
