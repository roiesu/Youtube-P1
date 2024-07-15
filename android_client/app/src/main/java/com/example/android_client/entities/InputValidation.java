package com.example.android_client.entities;

import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    private String name;
    private EditText input;
    public Pattern regex;
    private String reqs;

    public InputValidation(String name,  String regex, String reqs) {
        this.name = name;
        this.regex = Pattern.compile(regex);
        this.reqs = reqs;
    }
    public InputValidation(String name, String reqs){
        this.name= name;
        this.reqs=reqs;
    }

    public boolean match() {
        Matcher m = regex.matcher(input.getText().toString());
        return m.matches();
    }

    public String getInputText() {
        return this.input.getText().toString();
    }

    public void setInput(EditText input) {
        this.input = input;
    }

    public String getReqs() {
        return this.reqs;
    }

    public String getName() {
        return name;
    }
}

