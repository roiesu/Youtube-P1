package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.Comment;
import com.example.android_client.repositories.CommentListRepository;

import java.util.List;

public class CommentListViewModel extends ViewModel {
    private MutableLiveData<List<Comment>> comments;
    private CommentListRepository repository;
    public CommentListViewModel(){
        this.repository = new CommentListRepository();
        comments = repository.get();
    }
    public MutableLiveData<List<Comment>> getComments(){
        if(comments==null){
            return new MutableLiveData<>();
        }
        return comments;
    }
    public void init(LifecycleOwner lifecycleOwner){
        repository.init(lifecycleOwner);
    }
}
