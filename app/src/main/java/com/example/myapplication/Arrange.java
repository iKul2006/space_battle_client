package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.model.Field;
import com.example.myapplication.model.Infrastructure;
import com.example.myapplication.model.MyDraw;
import com.example.myapplication.model.Spaceship;
import com.example.myapplication.model.SpaceshipState;

import java.util.Random;

public class Arrange extends AppCompatActivity {
    Button btn;
    Button btnRotate;
    ConstraintLayout conL;
    private MyDraw myDraw;
    public static float size;

    @SuppressLint({"WrongViewCast", "SourceLockedOrientationActivity"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new MyDraw(this));
        setContentView(R.layout.activity_arrange);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btn = findViewById(R.id.btnPlay);
        btnRotate = findViewById(R.id.btnRotate);
        ConstraintLayout lay = findViewById(R.id.lay);
        conL = findViewById(R.id.conL);
        size = conL.getHeight() / 11;
        Infrastructure.createField();
        Infrastructure.createAdversaryField();
        Infrastructure.createSpaceShips();
        Infrastructure.createSpaceShipsForAdversary();
        Infrastructure.isFinished = false;
        myDraw = new MyDraw(this);
        lay.addView(myDraw,  new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                if (Infrastructure.field.spaceshipsInField.size() == 10) {
                    programTakeSpaceships(Infrastructure.adversarySpaceships, Infrastructure.adversaryField);
                    Intent intent = new Intent(Arrange.this, Battle.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(Arrange.this, "Размещены не все корабли", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.btnRotate:
                rotate(myDraw.selectedForRotationSpaceship, Infrastructure.field);
                myDraw.postInvalidate();
                break;
        }
    }

    //Расставляет корабли сопернику (пока что по рандомно выбранной заданной расстановкой)
    public void programTakeSpaceships (Spaceship[] spaceships, Field field){
        Random random = new Random();
        int r = random.nextInt(5);
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
                break;
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
                break;
            case 2:
                spaceships[0].takeSpaceShipInField(5, 9, field);
                spaceships[1].takeSpaceShipInField(2, 1, field);
                spaceships[2].takeSpaceShipInField(7, 2, field);
                spaceships[3].takeSpaceShipInField(1, 3, field);
                spaceships[4].takeSpaceShipInField(7, 0, field);
                spaceships[5].takeSpaceShipInField(2, 5, field);
                spaceships[6].takeSpaceShipInField(8, 4, field);
                spaceships[7].takeSpaceShipInField(5, 5, field);
                spaceships[8].takeSpaceShipInField(2, 7, field);
                spaceships[9].takeSpaceShipInField(6, 7, field);
                break;
            case 3:
                spaceships[0].takeSpaceShipInField(6, 4, field);
                spaceships[1].takeSpaceShipInField(3, 1, field);
                spaceships[2].takeSpaceShipInField(0, 8, field);
                spaceships[3].takeSpaceShipInField(0, 3, field);
                spaceships[4].takeSpaceShipInField(4, 6, field);
                spaceships[5].takeSpaceShipInField(7, 7, field);
                spaceships[6].takeSpaceShipInField(1, 0, field);
                spaceships[7].takeSpaceShipInField(8, 2, field);
                spaceships[8].takeSpaceShipInField(2, 5, field);
                spaceships[9].takeSpaceShipInField(7, 9, field);
                break;
            case 4:
                spaceships[0].takeSpaceShipInField(1, 4, field);
                spaceships[1].takeSpaceShipInField(7, 0, field);
                spaceships[2].takeSpaceShipInField(6, 7, field);
                spaceships[3].takeSpaceShipInField(2, 1, field);
                spaceships[4].takeSpaceShipInField(8, 5, field);
                spaceships[5].takeSpaceShipInField(3, 9, field);
                spaceships[6].takeSpaceShipInField(8, 2, field);
                spaceships[7].takeSpaceShipInField(3, 6, field);
                spaceships[8].takeSpaceShipInField(1, 7, field);
                spaceships[9].takeSpaceShipInField(8, 9, field);
                break;
        }
    }

    // Поворачивает корабль
    public void rotate(Spaceship selectedSpaceship, Field field) {
        if (selectedSpaceship.state == SpaceshipState.TAKEN) {
            selectedSpaceship.rotateSpaceship(field);
        } else {
            Toast toast = Toast.makeText(this, "Корабль не поставлен на поле", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
