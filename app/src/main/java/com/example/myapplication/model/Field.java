package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import com.example.myapplication.Spaceship;
import com.example.myapplication.Cell;

import java.util.ArrayList;

public class Field {
    public int x = 10, y = 10;
    public float startX, startY;
    private float cellSize;
    public Cell[][] cells;
    public ArrayList<Spaceship> spaceshipsInField = new ArrayList<>();

    public Field () {
        cells = new com.example.myapplication.Cell[x][y];
        for (int a = 0; a < x; a++) {
            for (int b = 0; b < y; b++) {
                cells[a][b] = new com.example.myapplication.Cell();
            }
        }
        this.cells = cells;
    }

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

        float x = (float) cellSize + startX;
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(x, startY, x, startY + cellSize * 10, paint);
            x += cellSize;
        }

        float y = (float) cellSize + startY;
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(startX, y, startX + cellSize * 10, y, paint);
            y += cellSize;
        }
    }

    public Pair<Integer, Integer> findCell(float x, float y) {
        int a = (int) ((x - startX) / cellSize);
        int b = (int) ((y - startY) / cellSize);

        if (a < 0 || a > 9 || b < 0 || b > 9) {
            a = -1;
            b = -1;
        }

        Pair pair = new Pair(new Integer(a), new Integer(b));
        return pair;
    }
}
