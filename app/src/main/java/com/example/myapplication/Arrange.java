package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.model.Infrastructure;
import com.example.myapplication.model.MyDraw;
import com.example.myapplication.Spaceship;

import java.util.Random;

public class Arrange extends AppCompatActivity {
    Button btn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new MyDraw(this));
        setContentView(R.layout.activity_arrange);

        btn = findViewById(R.id.btnPlay);
        ConstraintLayout lay = findViewById(R.id.lay);
        lay.addView(new MyDraw(this),  new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                if (Infrastructure.field.spaceshipsInField.size() == 10) {
                    programTakeSpaceships(Infrastructure.adversarySpaceships, Infrastructure.adversaryField);
                    Intent intent = new Intent(Arrange.this, Battle.class);
                    startActivity(intent);
                    break;
                } else {
                    Toast toast = Toast.makeText(Arrange.this, "Размещены не все корабли", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    public void programTakeSpaceships (Spaceship[] spaceships, com.example.myapplication.Field field){
        Random random = new Random();
        int r = random.nextInt(2);
        switch (r) {
            case 0:
                spaceships[0].takeSpaceShipInField(3, 1, field);
                spaceships[1].takeSpaceShipInField(2, 5, field);
                spaceships[2].takeSpaceShipInField(7, 6, field);
                spaceships[3].takeSpaceShipInField(0, 3, field);
                spaceships[4].takeSpaceShipInField(3, 8, field);
                spaceships[5].takeSpaceShipInField(7, 9, field);
                spaceships[6].takeSpaceShipInField(9, 1, field);
                spaceships[7].takeSpaceShipInField(9, 3, field);
                spaceships[8].takeSpaceShipInField(6, 4, field);
                spaceships[9].takeSpaceShipInField(0, 7, field);
            case 1:
                spaceships[0].takeSpaceShipInField(8, 4, field);
                spaceships[1].takeSpaceShipInField(1, 0, field);
                spaceships[2].takeSpaceShipInField(1, 6, field);
                spaceships[3].takeSpaceShipInField(5, 1, field);
                spaceships[4].takeSpaceShipInField(8, 1, field);
                spaceships[5].takeSpaceShipInField(7, 6, field);
                spaceships[6].takeSpaceShipInField(7, 3, field);
                spaceships[7].takeSpaceShipInField(3, 4, field);
                spaceships[8].takeSpaceShipInField(2, 9, field);
                spaceships[9].takeSpaceShipInField(9, 9, field);

                /*for (int i = 0; i < 10; i++) {
            boolean b = false;
            while (!b) {
                int x = random.nextInt(10 - spaceships[i].cellCount);
                int y = random.nextInt(9);
                if (spaceships[i].takeSpaceShipInField(x, y, field)) {
                    field.spaceshipsInField.add(spaceships[i]);
                    b = true;
                }
            }*/
        }
    }
}
