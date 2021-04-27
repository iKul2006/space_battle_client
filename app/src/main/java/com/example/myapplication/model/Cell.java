package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    public int idSpaceShip;
    public boolean isFired = false;
    public float x;
    public float y;

    public void fillCell(Canvas canvas, float cellSize, int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(x, y, x + cellSize, y + cellSize, paint);
    }

    public void fire(Canvas canvas, float cellSize) {
        isFired = true;
        fillCell(canvas, cellSize, Color.RED);
    }
}
