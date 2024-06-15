package com.example.android_client.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_client.R;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private class inputValidation {
        private String name;
        private EditText input;
        private Pattern regex;

        public inputValidation(String name, EditText input, String regex) {
            this.name = name;
            this.input = input;
            this.regex = Pattern.compile(regex);
        }

        public boolean match() {
            Matcher m = regex.matcher(input.getText().toString());
            return m.matches();
        }

        public String getInputText() {
            return this.input.getText().toString();
        }
    }

    private inputValidation[] inputs = new inputValidation[4];
    private Button submit;

    private void register() {
        TextView test = findViewById(R.id.test);
        int i=0;
        for(;i<4;i++){
            if(!inputs[i].match()){
                break;
            }
        }
        if(i<4){
            test.setText(i+"");
            return;
        }
        else if(inputs[1].getInputText().equals(inputs[2].getInputText())){
            test.setText("OK");
            return;
        }
        test.setText("Password validation must be the same as password");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        inputs[0] = new inputValidation("username", findViewById(R.id.usernameInput), "^[\\w\\d!@#$%^&*-_]{6,}$");
        inputs[1] = new inputValidation("password", findViewById(R.id.passwordInput), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?#$%&*+,\\-./@^_{}|~])[\\w\\d!?#$%&*+,\\-./@^{}|~]{8,}$");
        inputs[2] = new inputValidation("validatePassword", findViewById(R.id.vPasswordInput), ".+");
        inputs[3] = new inputValidation("name", findViewById(R.id.nameInput), "^[\\w\\d-_!@#$%^&*]+( [\\w\\d-_!@#$%^&*]+)*$");
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            register();
        });
    }
}
