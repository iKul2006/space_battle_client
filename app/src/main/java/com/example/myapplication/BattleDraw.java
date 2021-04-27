package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.myapplication.Cell;

import static com.example.myapplication.model.Infrastructure.adversaryField;
import static com.example.myapplication.model.Infrastructure.field;

public class BattleDraw extends View {
    @SuppressLint("ResourceType")
    ImageView imageView = findViewById(R.drawable.image);

    float cellSize = 60.8f;
    private boolean isFinished = false;
    private ArrayList<Cell> firedCells = new ArrayList<>();
    private ArrayList<Cell> notFiredCells = new ArrayList<>();
    public BattleDraw(Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        paint.setARGB(0, 55, 0, 179);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        field.draw(canvas, 32, 32, cellSize);
        adversaryField.draw(canvas, 720, 32, cellSize);

        for (Cell cell : firedCells){
            cell.fire(canvas, cellSize);
        }
        for (Cell cell : notFiredCells){
            cell.fillCell(canvas, cellSize, Color.WHITE);
        }

        isFinished = true;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++){
                if (adversaryField.cells[i][j].idSpaceShip != 0 && !adversaryField.cells[i][j].isFired){
                    isFinished = false;
                }
            }
        if (isFinished) {
            Toast toast = Toast.makeText(getContext(), "Победа!", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(i);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            Integer a = adversaryField.findCell(x, y).first;
            Integer b = adversaryField.findCell(x, y).second;
            int cellX = a.intValue();
            int cellY = b.intValue();

            if (cellX >= 0 && cellX <= 9 && cellY >= 0 && cellY <= 9) {
                double newX = cellX * cellSize + adversaryField.startX;
                double newY = cellY * cellSize + adversaryField.startY;
                adversaryField.cells[cellX][cellY].x = (float)newX;
                adversaryField.cells[cellX][cellY].y = (float)newY;
                if (adversaryField.cells[cellX][cellY].idSpaceShip != 0) {
                    if (!firedCells.contains(adversaryField.cells[cellX][cellY])) {
                        firedCells.add(adversaryField.cells[cellX][cellY]);
                    }
                } else {
                    if (!notFiredCells.contains(adversaryField.cells[cellX][cellY])) {
                        notFiredCells.add(adversaryField.cells[cellX][cellY]);
                    }
                }
            }
        }
        postInvalidate();
        return true;
    }
}
