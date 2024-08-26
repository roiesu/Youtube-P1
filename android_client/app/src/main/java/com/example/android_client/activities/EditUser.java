package com.example.android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.R;
import com.example.android_client.DataManager;
import com.example.android_client.view_models.UserViewModel;

public class EditUser extends MediaPickerActivity {
    private ImageView goBack;
    private EditText nameInput, passwordInput;
    private Button submitDetailsButton, deleteUserButton;
    private UserViewModel userViewModel;
    private boolean initialized = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_details);
        this.targetImageView = findViewById(R.id.profilePicture);
        this.uploadImageButton = findViewById(R.id.changImageButton);
        this.uploadImageButton.setOnClickListener(l -> {
            this.pickImage();
        });
        this.goBack = findViewById(R.id.goBack);
        this.nameInput = findViewById(R.id.userName);
        this.passwordInput = findViewById(R.id.userPassword);
        this.deleteUserButton = findViewById(R.id.deleteUserButton);
        this.submitDetailsButton = findViewById(R.id.updateDetailsButton);
        userViewModel = new UserViewModel(this);
        MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
        finished.observe(this, data -> {
            if (data) {
                Intent intent = new Intent();
                intent.putExtra("edited", false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        userViewModel.getUserData().observe(this, data -> {
            if (data != null && finished.getValue() == false) {
                this.passwordInput.setText(data.getPassword());
                this.nameInput.setText(data.getName());
                Glide.with(this).load(data.getImageFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(this.targetImageView);
                initialized = true;
            } else if (initialized == false) {
                finish();
            }
        });

        goBack.setOnClickListener(l -> {
            finish();
        });
        deleteUserButton.setOnClickListener(l -> {
            userViewModel.delete(finished);
        });
        submitDetailsButton.setOnClickListener(l->{
            userViewModel.edit();
        });
        userViewModel.getFullUserDetails(DataManager.getCurrentUsername());
    }
}
