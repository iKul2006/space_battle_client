package com.example.myapplication.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

// Класс клетки поля
public class Cell {
    private Field field;
    public int idSpaceShip;
    public int cellX;
    public int cellY;
    public boolean isShooted = false;
    public int damageSide = 1;

    public Cell(int cellX, int cellY, Field field) {
        this.field = field;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    // Размер клетки
    private float getCellSize() {
        return field.getCellSize();
    }

    // Находит координату X начала клетки
    private float getX() {
        double newX = cellX * getCellSize() + field.getStartX();
        float x = (float)newX;
        return x;
    }

    // Находит координату Y начала клетки
    private float getY() {
        double newY = cellY * getCellSize() + field.getStartY();
        float y = (float)newY;
        return y;
    }

    // Рисует клетку на канве
    private void drawCell(Canvas canvas, int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        float x = getX();
        float y = getY();
        canvas.drawRect(x, y, x + getCellSize(), y + getCellSize(), paint);
    }

    // Рисует клетку, когда в корабль "попали"
    public void fire(Canvas canvas) {
        drawCell(canvas, Color.RED);
    }

    // Рисует клетку, когда в корабль "не попали"
    public void notFire(Canvas canvas) {
        drawCell(canvas, Color.WHITE);
    }

    public void shoot() {
        isShooted = true;
    }
}
