package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import com.example.myapplication.model.Cell;
import com.example.myapplication.model.Field;
import com.example.myapplication.model.Infrastructure;
import com.example.myapplication.model.Spaceship;
import com.example.myapplication.model.SpaceshipState;

import static com.example.myapplication.model.Infrastructure.adversaryField;
import static com.example.myapplication.model.Infrastructure.adversarySpaceships;
import static com.example.myapplication.model.Infrastructure.field;
import static com.example.myapplication.model.Infrastructure.player;
import static com.example.myapplication.model.Infrastructure.spaceships;

public class BattleDraw extends View {
    @SuppressLint("ResourceType")
    float cellSize;
    public static boolean isFinished = false;

    private ArrayList<Cell> adversaryFiredCells = new ArrayList<>();
    private ArrayList<Cell> adversaryNotFiredCells = new ArrayList<>();
    public boolean blockResult = false;
    int x, y;

    public BattleDraw(Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        cellSize = Infrastructure.cellSize(canvas);

        paint.setARGB(0, 55, 0, 179);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        field.draw(canvas, cellSize / 2, cellSize / 2, cellSize);
        adversaryField.draw(canvas, cellSize * 12, cellSize / 2, cellSize);

        //Отрисовывает клетки с кораблями, в которые стреляли, красным
        for (Cell cell : adversaryField.firedCells){
            cell.fire(canvas);
        }
        //Отрисовывает клетки без кораблей, в которые стреляли, белым
        for (Cell cell : adversaryField.notFiredCells){
            cell.fillCell(canvas, Color.WHITE);
        }

        //Стреляет противник
        programFire(field);
        //Отрисовывает клетки с кораблями, в которые стреляли, красным
        for (Cell cell : field.firedCells){
            cell.fire(canvas);
        }
        //Отрисовывает клетки без кораблей, в которые стреляли, белым
        for (Cell cell : field.notFiredCells){
            cell.fillCell(canvas, Color.WHITE);
        }

        if (isFinished) {
            Infrastructure.winner = true;
            if (isGameFinished() == 2) {
                Infrastructure.winner = false;
                if (!blockResult) {
                    player.vsProgramLosses++;
                    blockResult = true;
                }
            } else if (!blockResult) {
                player.vsProgramVictories++;
                blockResult = true;
            }
            Intent i = new Intent(getContext(), BattleResult.class);
            getContext().startActivity(i);
            Battle.finishActivity = true;
        }
    }

    //Проверяее, закончилась или нет игра
    private int isGameFinished() {
        int result = 1;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++){
                if (adversaryField.cells[i][j].idSpaceShip != 0 && !adversaryField.firedCells.contains(adversaryField.cells[i][j])){
                    result = 0;
                    break;
                }
            }
        if (result == 1) {
            return 1;
        }
        result = 2;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++){
                if (field.cells[i][j].idSpaceShip != 0 && !field.firedCells.contains(field.cells[i][j])){
                    result = 0;
                }
            }
        return result;
    }

    boolean turn = true; // = true, если ход игрока, false, если ход противника
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!turn || isFinished) return false;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            // Находит номера клеток cellX, cellY, в которые выстрелили
            float thisX = event.getX();
            float thisY = event.getY();
            Integer a = adversaryField.findCell(thisX, thisY).first;
            Integer b = adversaryField.findCell(thisX, thisY).second;
            int cellX = a.intValue();
            int cellY = b.intValue();

            // Если клетка в поле, проверяет, попали или нет в корабль противника
            if (cellX >= 0 && cellX <= 9 && cellY >= 0 && cellY <= 9) {
                int idSpaceship = adversaryField.shoot(cellX, cellY);
                if (idSpaceship > 0) {
                    damageSpaceship(idSpaceship, adversarySpaceships, adversaryField, cellX, cellY);
                    turn = true;
                } else {
                    turn = false;
                }
            }
        }
        postInvalidate();
        return true;
    }

    private int shootX;
    private int shootY;

    // Находит клетку, в которую надо выстрелить
    private void setShootCoordinates() {
        ArrayList<Cell> list = new ArrayList<>();
        for (int i = 0; i < field.cells.length; i++) {
            for (int j = 0; j < field.cells[i].length; j++) {
                if (!field.cells[i][j].isShooted) {
                    list.add(field.cells[i][j]);
                }
            }
        }
        Random random = new Random();
        int num = random.nextInt(list.size());
        Cell cell = list.get(num);
        shootX = cell.cellX;
        shootY = cell.cellY;
    }

    private void programFire(Field field) {
        while (!turn) {
            setShootCoordinates();
            int idSpaceShip = field.shoot(shootX, shootY);
            if (idSpaceShip > 0) {
                damageSpaceship(idSpaceShip, spaceships, field, shootX, shootY);
            } else {
                turn = true;
                break;
            }
        }
    }

    private void damageSpaceship(int idSpaceShip, Spaceship[] ships, Field field, int cellX, int cellY) {
        for (int i = 0; i < ships.length; i++) {
            if (ships[i].id == idSpaceShip) {
                ships[i].damage();

                if (ships[i].state == SpaceshipState.KILLED) {
                    //Если корабль убит, окружает клетки вокруг него
                    aroundShip(field, ships[i]);
                    if (isGameFinished() > 0) {
                        isFinished = true;
                    }
                }
                break;
            }
        }
    }

    //Окружает корабль клетками, если он убит
    private void aroundShip(Field field, Spaceship ship) {
        int x = ship.currentCellX;
        int y = ship.currentCellY;
        for (int i = 0; i < ship.cellCount; i++) {
            around(field, x, y);
            if (!ship.isRotated) x++;
            else y++;
        }
    }

    private void around(Field field, int x, int y) {
        addNotFiredCell(field, x - 1, y - 1);
        addNotFiredCell(field, x - 1, y);
        addNotFiredCell(field, x - 1, y + 1);
        addNotFiredCell(field, x + 1, y - 1);
        addNotFiredCell(field, x + 1, y);
        addNotFiredCell(field, x + 1, y + 1);
        addNotFiredCell(field, x, y - 1);
        addNotFiredCell(field, x, y + 1);
    }

    private void addNotFiredCell(Field field, int cellX, int cellY) {
        if (cellX >= 0 && cellX <= 9
                && cellY >= 0 && cellY <= 9) {
            field.shoot(cellX, cellY);
        }
    }
}
