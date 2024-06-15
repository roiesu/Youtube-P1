package com.example.android_client.activities;

import android.net.Uri;
import android.os.Bundle;
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

import com.example.android_client.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private class inputValidation {
        private String name;
        private EditText input;
        private Pattern regex;
        private String reqs;

        public inputValidation(String name, EditText input, String regex,String reqs) {
            this.name = name;
            this.input = input;
            this.regex = Pattern.compile(regex);
            this.reqs=reqs;
        }

        public boolean match() {
            Matcher m = regex.matcher(input.getText().toString());
            return m.matches();
        }

        public String getInputText() {
            return this.input.getText().toString();
        }
        public String getReqs(){
            return this.reqs;
        }
    }

    private inputValidation[] inputs = new inputValidation[4];
    private Button submit;
    private Button uploadImageButton;

    private ImageView previewImage;
    private Uri imageUri;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    private void register() {
        TextView test = findViewById(R.id.test);
        int i=0;
        for(;i<4;i++){
            if(!inputs[i].match()){
                test.setText(inputs[i].reqs);
                return;
            }
        }
        if(!inputs[1].getInputText().equals(inputs[2].getInputText())){
            test.setText(inputs[2].reqs);
            return;
        }
        else if(imageUri==null) {
            test.setText("No image chosen");
            return;
        }
        test.setText("Ok");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        inputs[0] = new inputValidation("username", findViewById(R.id.usernameInput), "^[\\w\\d!@#$%^&*-_]{6,}$","Username needs to be at least 6 letters long. Must contain only letters, numbers and special symbols.");
        inputs[1] = new inputValidation("password", findViewById(R.id.passwordInput), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?#$%&*+,\\-./@^_{}|~])[\\w\\d!?#$%&*+,\\-./@^{}|~]{8,}$","Password needs to be at least 8 letters long, and must contain- a small letter, a capital letter, a number and a special symbol.");
        inputs[2] = new inputValidation("validatePassword", findViewById(R.id.vPasswordInput), ".+","Password validation must be identical to the password");
        inputs[3] = new inputValidation("name", findViewById(R.id.nameInput), "^[\\w\\d-_!@#$%^&*]+( [\\w\\d-_!@#$%^&*]+)*$","Name can contain letters, numbers, special letters and spaces.");
        submit = (Button) findViewById(R.id.submit);
        previewImage = (ImageView) findViewById(R.id.imagePreview);
        uploadImageButton = (Button) findViewById(R.id.imageInput);
        pickMedia= registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                imageUri=uri;
                previewImage.setImageURI(imageUri);
            }
        });
        submit.setOnClickListener(view -> {
            register();
        });
        uploadImageButton.setOnClickListener(view->{pickImage();});

    }
    public void pickImage(){
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

}
