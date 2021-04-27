package com.example.myapplication.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Field;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Spaceship;

import static com.example.myapplication.model.Infrastructure.field;
import static com.example.myapplication.model.Infrastructure.spaceships;

public class MyDraw extends View {
    private Spaceship selectedSpaceship;
    private int cellX;
    private int cellY;
    private float x, y;
    private boolean drawShip = false;

    public MyDraw(Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);



        float cellSize = 60.8f;
        field.draw(canvas, 32, 32, cellSize);

        drawSpaceships(canvas, cellSize);

        if (drawShip && selectedSpaceship != null && cellX != -1) {
            selectedSpaceship.currentX = field.startX + cellX * cellSize;
            selectedSpaceship.currentY = field.startY + cellY * cellSize;
            if (!field.spaceshipsInField.contains(selectedSpaceship)) {
                field.spaceshipsInField.add(selectedSpaceship);
            }
            paint.setARGB(255, 55, 0, 179);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawRect((float) selectedSpaceship.startX - 10, (float) selectedSpaceship.startY - 10,
                    (float) (selectedSpaceship.startX + selectedSpaceship.cellCount * 60.8 + 10),
                    (float) (selectedSpaceship.startY + 60.5 + 10), paint);
            selectedSpaceship = null;
            cellX = -1;
            cellY = -1;
        }
        for (Spaceship ship : field.spaceshipsInField) {
            ship.draw(cellSize, canvas, ship.currentX, ship.currentY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        this.x = x;
        this.y = y;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            selectedSpaceship = null;
            cellX = -1;
            for (int i = 0; i < spaceships.length; i++) {
                if (spaceships[i].isPointInSpaceship(x, y)) {
                    selectedSpaceship = spaceships[i];
                    break;
                }
            }
        }
        if (selectedSpaceship != null && event.getAction() == MotionEvent.ACTION_UP) {
            if (checkXY(x, y)) {
                drawShip = true;
            } else {
                selectedSpaceship = null;
                Toast toast = Toast.makeText(getContext(),"Корабль размещен неверно", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        postInvalidate(); //
        return true;
    }

    private void drawSpaceships(Canvas canvas, double size) {
        for (int i = 0; i < spaceships.length; i++) {
            if (!field.spaceshipsInField.contains(spaceships[i])) {
                spaceships[i].draw(size, canvas, spaceships[i].startX, spaceships[i].startY);
            }
        }
    }
    private boolean checkXY(float x, float y) {
        Integer a = field.findCell(x, y).first;
        Integer b = field.findCell(x, y).second;
        cellX = a.intValue();
        cellY = b.intValue();
        return cellX != -1 && selectedSpaceship.takeSpaceShipInField(cellX, cellY, field);
    }

    private void redrawSpaceship(Canvas canvas, Spaceship ship) {
        Paint paint = new Paint();
        paint.setARGB(255, 3, 218, 197);
        paint.setStyle(Paint.Style.FILL);
        ship.draw(60.8, canvas, (float) (field.startX + cellX * 60.8), (float) (field.startY + cellY * 60.8));


    }
}