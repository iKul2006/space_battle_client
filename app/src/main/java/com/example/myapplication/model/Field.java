package com.example.myapplication.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;

import java.util.ArrayList;

// Класс поля игрока
public class Field {
    private int countX = 10, countY = 10; // количество клеток в поле
    private float startX, startY;
    private float cellSize;
    public Cell[][] cells;
    public ArrayList<Spaceship> spaceshipsInField = new ArrayList<>();
    public ArrayList<Cell> firedCells = new ArrayList<>();
    public ArrayList<Cell> notFiredCells = new ArrayList<>();

    Field() {
        cells = new Cell[countX][countY];
        for (int a = 0; a < countX; a++) {
            for (int b = 0; b < countY; b++) {
                cells[a][b] = new Cell(a, b, this);
            }
        }
    }

    // Рисует поле на заданной канве, начиная с точки startX, startY и клетки размером cellSize
    public void draw(Canvas canvas, float startX, float startY, float cellSize) {
        this.startX = startX;
        this.startY = startY;
        this.cellSize = cellSize;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        paint.setARGB(255, 98, 0, 238);
        canvas.drawRect(startX, startY, startX + cellSize * 10, startY + cellSize * 10, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawRect(startX, startY, startX + cellSize * 10, startY + cellSize * 10, paint);

        float x = cellSize + startX;
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(x, startY, x, startY + cellSize * 10, paint);
            x += cellSize;
        }

        float y = cellSize + startY;
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(startX, y, startX + cellSize * 10, y, paint);
            y += cellSize;
        }
    }

    // Находит клетку по координатам экрана x, y
    public Pair<Integer, Integer> findCell(float x, float y) {
        int a = (int) ((x - startX) / cellSize);
        int b = (int) ((y - startY) / cellSize);

        if (a < 0 || a > 9 || b < 0 || b > 9) {
            a = -1;
            b = -1;
        }

        return new Pair<Integer, Integer>(a, b);
    }

    // Возвращает id корабля, в который попали или 0 если не попали
    public int shoot(int cellX, int cellY) {
        //Если в клетке стоит корабль, добаляет ее в firedCells (клетки, которые закрашиваются красным)
        if (cells[cellX][cellY].idSpaceShip != 0) {
            if (!firedCells.contains(cells[cellX][cellY])) {
                firedCells.add(cells[cellX][cellY]);
            }
        } else { //Если промазали
            //Если еще не стреляли в эту клетку
            if (!notFiredCells.contains(cells[cellX][cellY])) {
                //Добавляет ее в список notFiredCells (клетки, которые закрашиваются белым)
                notFiredCells.add(cells[cellX][cellY]);
            }
        }
        cells[cellX][cellY].shoot();
        return cells[cellX][cellY].idSpaceShip;
    }

    float getCellSize() {
        return cellSize;
    }

    float getStartX() {
        return startX;
    }

    float getStartY() {
        return startY;
    }
}
