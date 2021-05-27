package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

import static com.example.myapplication.model.Infrastructure.player;

public class MainActivity extends AppCompatActivity {
    Button btnProfile;
    Button btnPlayWithProgram;
    Button btnPlayWithPlayer;
    Button btnSettings;
    //public static File info;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        startService(new Intent(this, MediaService.class)); //Музыка
        btnProfile = findViewById(R.id.btnProfile);
        btnPlayWithProgram = findViewById(R.id.btnPlayWithProgram);
        btnPlayWithPlayer = findViewById(R.id.btnPlayWithPlayer);
        btnSettings = findViewById(R.id.btnSettings);
        //info = new File("playerInfo.txt"); //Недоделанная запись в файл

        Random random = new Random();

        if (player.id == 0) {
            player.id = random.nextInt(999999999);
            player.name = String.valueOf(player.id);
            player.victories = 0;
            player.losses = 0;
        }

        //Недоделанная запись в файл
        /*try {
            FileReader reader = new FileReader(info);
            //String name = reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //Если пользователь не зарегистрирован, отправляет на регистрацию
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
                finish();
                break;
            case R.id.btnPlayWithPlayer:
                Toast toast = Toast.makeText(
                        this,
                        "Подожди следующее обновление! Эта функция еще не доступна",
                        Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.btnSettings:
                i = new Intent(MainActivity.this, Settings.class);
                startActivity(i);
                break;
        }
    }
}