package com.example.android_client.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.InputValidationAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.InputValidation;
import com.example.android_client.entities.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private ArrayList<InputValidation> inputs;

    private RecyclerView inputList;
    private Button submit;
    private Button uploadImageButton;

    private ImageView previewImage;
    private Uri imageUri;
    private TextView errorView;

    private Button change;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        inputs = new ArrayList<>();
        inputList = findViewById(R.id.recyclerView);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        String [] inputNames =getResources().getStringArray(R.array.inputNames);
        String [] inputRegex =getResources().getStringArray(R.array.inputRegex);
        String [] inputReqs =getResources().getStringArray(R.array.inputRequierments);
        for(int i=0;i<4;i++){
            inputs.add(new InputValidation(inputNames[i],inputRegex[i],inputReqs[i]));
        }
        InputValidationAdapter adapter= new InputValidationAdapter(this,inputs,true);
        inputList.setAdapter(adapter);
        submit = findViewById(R.id.submit);
        previewImage = findViewById(R.id.imagePreview);
        uploadImageButton =findViewById(R.id.imageInput);
        errorView = findViewById(R.id.validationError);
        change = findViewById(R.id.signin);
        change.setOnClickListener(l->{
            Intent intent = new Intent(l.getContext(),SignIn.class);
            startActivity(intent);
        });
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                imageUri = uri;
                previewImage.setImageURI(imageUri);
            }
        });
        submit.setOnClickListener(view -> {
            register();
        });
        uploadImageButton.setOnClickListener(view -> {
            pickImage();
        });

    }
    public void pickImage() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
    private void register() {
        for(InputValidation input:inputs){
            if(!input.match()){
                errorView.setText(input.getReqs());
                return;
            }
        }
        if (!inputs.get(1).getInputText().equals(inputs.get(2).getInputText())) {
            errorView.setText(inputs.get(2).getReqs());
            return;
        } else if (imageUri == null) {
            errorView.setText("No image chosen");
            return;
        } else if (DataManager.findUser(inputs.get(0).getInputText()) != null) {
            errorView.setText("User with that useranme already taken");
            return;
        }
        User newUser = new User(inputs.get(0).getInputText(),inputs.get(1).getInputText(),inputs.get(3).getInputText(), imageUri.toString());
        DataManager.addUser(newUser);
        DataManager.getInstance().setCurrentUser(newUser);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
