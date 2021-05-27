package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.model.Infrastructure;
import com.example.myapplication.model.MyDraw;

public class Battle extends AppCompatActivity {
    public static boolean finishActivity = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ConstraintLayout layout = findViewById(R.id.layout);
        layout.addView(new BattleDraw(this),  new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        if (finishActivity) {
            finish();
        }
    }
}