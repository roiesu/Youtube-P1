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
    public void editComment(Comment comment, String text, String videoUploader){
        this.repository.editComment(comment, text, videoUploader);
    }

    public void deleteComment(Comment comment, String videoUploader) {
        this.repository.deleteComment(comment, videoUploader);
    }
}
