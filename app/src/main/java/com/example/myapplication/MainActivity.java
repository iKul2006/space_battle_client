package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Random;

import com.example.myapplication.Player;

import static com.example.myapplication.model.Infrastructure.player;

public class MainActivity extends AppCompatActivity {
    Button btnProfile;
    Button btnPlayWithProgram;
    Button btnPlayWithPlayer;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnProfile = findViewById(R.id.btnProfile);
        btnPlayWithProgram = findViewById(R.id.btnPlayWithProgram);
        btnPlayWithPlayer = findViewById(R.id.btnPlayWithPlayer);
        btnSettings = findViewById(R.id.btnSettings);

        Random random = new Random();

        if (player.id == 0) {
            player.id = random.nextInt(999999999);
            player.name = String.valueOf(player.id);
            player.victories = 0;
            player.losses = 0;
        }

        if (player.name.equals(String.valueOf(player.id))) {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        }
    }
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnProfile:
                i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.btnPlayWithProgram:
                i = new Intent(MainActivity.this, Arrange.class);
                startActivity(i);
                break;
        }
    }
}
