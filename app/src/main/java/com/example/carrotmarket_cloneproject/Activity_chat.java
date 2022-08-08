package com.example.carrotmarket_cloneproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_chat extends AppCompatActivity {
    private Button btn_nickname;
    private ImageButton imgbtn_back, imgbtn_call, imgbtn_chat_menu, imgbtn_plus, imgbtn_chat_send;
    private EditText ed_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        btn_nickname = findViewById(R.id.button_nickname);
        imgbtn_back = findViewById(R.id.button_back);
        imgbtn_call = findViewById(R.id.button_call);
        imgbtn_chat_menu = findViewById(R.id.button_chat_menu);
        imgbtn_plus = findViewById(R.id.button_plus);
        imgbtn_chat_send = findViewById(R.id.button_chat_send);

    }
}