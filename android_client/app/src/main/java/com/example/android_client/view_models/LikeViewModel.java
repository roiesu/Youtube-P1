package com.example.android_client.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;
import com.example.android_client.repositories.LikeRepository;
import com.example.android_client.repositories.UserRepository;

public class LikeViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLiked;
    private LikeRepository repository;
    public LikeViewModel(String userId ,String videoId){
        this.repository = new LikeRepository(userId ,videoId);
        isLiked = repository.get();
    }
    public MutableLiveData<Boolean> getIsLiked(){
        if(isLiked==null){
            return new MutableLiveData<>();
        }
        return isLiked;
    }
}
