package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Infrastructure;

import static com.example.myapplication.model.Infrastructure.adversaryField;
import static com.example.myapplication.model.Infrastructure.field;
import static com.example.myapplication.model.Infrastructure.player;
import static com.example.myapplication.model.Infrastructure.spaceships;

public class BattleResult extends AppCompatActivity {
    ImageView winText;
    ImageView loseText;
    TextView exp;
    Button btnGo;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_result);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        winText = findViewById(R.id.winText);
        loseText = findViewById(R.id.loseText);
        exp = findViewById(R.id.exp);
        btnGo = findViewById(R.id.btnGoToMenu);

        //Очищает все после боя (но тут пока что еще есть баг)
        field.firedCells.clear();
        adversaryField.firedCells.clear();
        field.notFiredCells.clear();
        adversaryField.notFiredCells.clear();
        field.spaceshipsInField.clear();
        adversaryField.spaceshipsInField.clear();

        for (int i = 0; i < spaceships.length; i++) {
            spaceships[i].isRotated = false;
        }

        if (Infrastructure.winner) {
            winText.setVisibility(View.VISIBLE);
        } else {
            loseText.setVisibility(View.VISIBLE);
        }

        int experience = player.levelExperience();
        exp.setText(String.valueOf(experience));
        Battle.finishActivity = false;
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnGoToMenu:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
