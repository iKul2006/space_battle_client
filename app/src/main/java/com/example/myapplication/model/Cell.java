package com.example.myapplication.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    private Field field;
    public int idSpaceShip;
    public boolean isFired = false;
    public int cellX;
    public int cellY;
    public boolean isShooted = false;

    public Cell(int cellX, int cellY, Field field) {
        this.field = field;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    private float getCellSize() {
        return field.cellSize;
    }

    //Находит начало клетки по X
    private float getX() {
        double newX = cellX * getCellSize() + field.startX;
        float x = (float)newX;
        return x;
    }

    //Находит начало клетки по Y
    private float getY() {
        double newY = cellY * getCellSize() + field.startY;
        float y = (float)newY;
        return y;
    }

    public void fillCell(Canvas canvas, int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        float x = getX();
        float y = getY();
        canvas.drawRect(x, y, x + getCellSize(), y + getCellSize(), paint);
    }

    public void fire(Canvas canvas) {
        isFired = true;
        fillCell(canvas, Color.RED);
    }

    public void shoot() {
        isShooted = true;
    }

    public boolean isEmpty() {
        return idSpaceShip == 0;
    }
}
