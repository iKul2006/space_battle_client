package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.model.Player;

import static com.example.myapplication.MainActivity.*;
import static com.example.myapplication.model.Infrastructure.player;

public class ProfileActivity extends AppCompatActivity {
    Button btnExitFromProfile;
    TextView experienceLevel;
    TextView name;
    TextView offlineVictoriesCount;
    TextView offlineLossesCount;
    TextView victoriesCount;
    TextView lossesCount;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        btnExitFromProfile = findViewById(R.id.btnExitFromProfile);

        experienceLevel = findViewById(R.id.experienceLevel);
        experienceLevel.setText(String.valueOf(player.levelExperience()));

        name = findViewById(R.id.name);
        name.setText(String.valueOf(player.name));

        victoriesCount = findViewById(R.id.victoriesCount);
        victoriesCount.setText(String.valueOf(player.victories));

        lossesCount = findViewById(R.id.lossesCount);
        lossesCount.setText(String.valueOf(player.losses));

        offlineVictoriesCount = findViewById(R.id.offlineVictoriesCount);
        offlineVictoriesCount.setText(String.valueOf(player.vsProgramVictories));

        offlineLossesCount = findViewById(R.id.offlineLossesCount);
        offlineLossesCount.setText(String.valueOf(player.vsProgramLosses));
    }
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnExitFromProfile:
                finish();
                break;
        }
    }
}
