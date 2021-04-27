package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Player;

import static com.example.myapplication.MainActivity.*;
import static com.example.myapplication.model.Infrastructure.player;

public class ProfileActivity extends AppCompatActivity {
    Button btnExitFromProfile;
    TextView experienceLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnExitFromProfile = findViewById(R.id.btnExitFromProfile);
        experienceLevel = findViewById(R.id.experienceLevel);
        experienceLevel.setText(player.levelExperience());
    }
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnExitFromProfile:
                i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
