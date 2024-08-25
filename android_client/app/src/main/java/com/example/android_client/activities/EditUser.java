package com.example.android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.R;
import com.example.android_client.entities.DataManager;
import com.example.android_client.view_models.UserViewModel;

public class EditUser extends AppCompatActivity {
    private ImageView profilePicture, goBack;
    private EditText nameInput, passwordInput;
    private Button uploadImageButton, submitDetailsButton, deleteUserButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_details);
        this.profilePicture = findViewById(R.id.profilePicture);
        this.goBack = findViewById(R.id.goBack);
        this.nameInput = findViewById(R.id.userName);
        this.passwordInput = findViewById(R.id.userPassword);
        this.uploadImageButton = findViewById(R.id.changImageButton);
        this.deleteUserButton = findViewById(R.id.deleteUserButton);
        this.submitDetailsButton = findViewById(R.id.updateDetailsButton);
        userViewModel = new UserViewModel();
        userViewModel.getUserData().observe(this, data -> {
            if (data != null) {
                this.passwordInput.setText(data.getPassword());
                this.nameInput.setText(data.getName());
                Glide.with(this).load(data.getImageFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(this.profilePicture);
            }
        });

        goBack.setOnClickListener(l -> {
            finish();
        });
        userViewModel.getFullUserDetails(DataManager.getCurrentUsername());
    }
}
