package com.example.carrotmarket_cloneproject.login;

import androidx.appcompat.app.AppCompatActivity;
import com.example.carrotmarket_cloneproject.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    private Button btn_start;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        btn_start = findViewById(R.id.btn_start);
        btn_login = findViewById(R.id.btn_login);

        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, StartLocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}