package com.example.myapplication.model;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.text.Layout;
import android.view.Display;
import android.view.View;
import android.widget.AlphabetIndexer;

import com.example.myapplication.Arrange;
import com.example.myapplication.model.Field;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Spaceship;

public class Infrastructure {
    public static boolean winner;

    public static Player player = new Player();

    public static Field field;
    public static Field adversaryField;

    public static void createField() {
        field = new Field();
    }

    public static Spaceship[] spaceships = createSpaceShips();

    //Создает корабли для игрока
    public static Spaceship[] createSpaceShips() {
        //float size = Arrange.size; //Незаконченное исправление бага
        float size = 60.8f;
        Spaceship[] spaceships = new Spaceship[10];
        spaceships[0] = new Spaceship(1, 4, size * 12, size / 2);
        spaceships[1] = new Spaceship(2, 3, size * 12, size * 2.5);
        spaceships[2] = new Spaceship(3, 3, size * 16, size * 2.5);
        spaceships[3] = new Spaceship(4, 2, size * 17, size / 2);
        spaceships[4] = new Spaceship(5, 2, size * 20, size / 2);
        spaceships[5] = new Spaceship(6, 2, size * 20, size * 2.5);
        spaceships[6] = new Spaceship(7, 1, size * 12, size * 4.5);
        spaceships[7] = new Spaceship(8, 1, size * 15, size * 4.5);
        spaceships[8] = new Spaceship(9, 1, size * 18, size * 4.5);
        spaceships[9] = new Spaceship(10, 1, size * 21, size * 4.5);
        return spaceships;
    }

    public static void createAdversaryField() {
        adversaryField = new Field();
    }
    public static Spaceship[] adversarySpaceships = createSpaceShipsForAdversary();

    //Создает корабли для соперника
    public static Spaceship[] createSpaceShipsForAdversary() {
        Spaceship[] adversarySpaceships = new Spaceship[10];
        adversarySpaceships[0] = new Spaceship(1, 4);
        adversarySpaceships[1] = new Spaceship(2, 3);
        adversarySpaceships[2] = new Spaceship(3, 3);
        adversarySpaceships[3] = new Spaceship(4, 2);
        adversarySpaceships[4] = new Spaceship(5, 2);
        adversarySpaceships[5] = new Spaceship(6, 2);
        adversarySpaceships[6] = new Spaceship(7, 1);
        adversarySpaceships[7] = new Spaceship(8, 1);
        adversarySpaceships[8] = new Spaceship(9, 1);
        adversarySpaceships[9] = new Spaceship(10, 1);
        return adversarySpaceships;
    }

    //Проверяет введенное имя пользователся на то, что оно не пустое и не содержит пробелов
    public static boolean checkName(String name) {
        if (name.isEmpty()) return false;
        for (int i = 0; i < name.length(); i++) {
            Character symbol = name.charAt(i);
            if (Character.isSpaceChar(symbol)) return false;
        }
        return true;
    }

    //Вычисляет длину стороны клетки
    public static float cellSize(Canvas canvas) {
        int height = canvas.getHeight();
        return height / 11;
    }
}
