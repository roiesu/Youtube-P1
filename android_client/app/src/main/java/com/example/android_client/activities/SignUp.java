package com.example.android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.helpers.MediaPickerActivity;
import com.example.android_client.adapters.InputValidationAdapter;
import com.example.android_client.entities.InputValidation;
import com.example.android_client.entities.User;
import com.example.android_client.view_models.UserViewModel;

import java.util.ArrayList;

public class SignUp extends MediaPickerActivity {

    private ArrayList<InputValidation> inputs;

    private RecyclerView inputList;
    private Button submit;
    private Button goToSignInButton;
    private UserViewModel userViewModel;

    private final int REQUEST_CAMERA_CODE = 200;

    private void initInputs() {
        inputs = new ArrayList<>();
        inputList = findViewById(R.id.recyclerView);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        String[] inputNames = getResources().getStringArray(R.array.inputNames);
        String[] inputReqs = getResources().getStringArray(R.array.inputRequierments);
        for (int i = 0; i < 4; i++) {
            inputs.add(new InputValidation(inputNames[i], inputReqs[i]));
        }
        InputValidationAdapter adapter = new InputValidationAdapter(this, inputs, true);
        inputList.setAdapter(adapter);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        targetImageView = findViewById(R.id.imagePreview);
        uploadImageButton = findViewById(R.id.imageInput);
        uploadImageButton.setOnClickListener(view -> {
            pickImage();
        });
        userViewModel = new UserViewModel(this);
        userViewModel.getUserData().observe(this, user -> {
            if (user == null) {
                Intent intent = new Intent(this, MainPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        initInputs();
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            register();
        });
        goToSignInButton = findViewById(R.id.signin);
        goToSignInButton.setOnClickListener(l -> {
            startActivity(new Intent(this,SignIn.class));
        });


    }

    private void register() {
        if(imageUri==null){
            ContextApplication.showToast("Image is required");
            return;
        }
        else if(inputs.get(1).equals(inputs.get(2))){
            ContextApplication.showToast("Password validation doesn't match");
            return;
        }
        User newUser = new User(null, inputs.get(0).getInputText(), inputs.get(1).getInputText(), inputs.get(3).getInputText(), Utilities.imageUriToBase64(this,imageUri));
        userViewModel.getUserData().setValue(newUser);
        userViewModel.create();
    }
}
