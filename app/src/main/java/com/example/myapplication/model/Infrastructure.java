package com.example.myapplication.model;

import com.example.myapplication.Field;
import com.example.myapplication.Player;
import com.example.myapplication.Spaceship;

public class Infrastructure {
    public static Player player = new Player();

    public static Field field = new Field();

    public static Spaceship[] spaceships = createSpaceShips();
    private static Spaceship[] createSpaceShips() {
        Spaceship[] spaceships = new Spaceship[10];
        spaceships[0] = new Spaceship(1, 4, 704, 32);
        spaceships[1] = new Spaceship(2, 3, 704, 153.6);
        spaceships[2] = new Spaceship(3, 3, 947.2, 153.6);
        spaceships[3] = new Spaceship(4, 2, 1008, 32);
        spaceships[4] = new Spaceship(5, 2, 1190.4, 32);
        spaceships[5] = new Spaceship(6, 2, 1190.4, 153.6);
        spaceships[6] = new Spaceship(7, 1, 704, 275.2);
        spaceships[7] = new Spaceship(8, 1, 886.4, 275.2);
        spaceships[8] = new Spaceship(9, 1, 1068.8, 275.2);
        spaceships[9] = new Spaceship(10, 1, 1251.2, 275.2);
        return spaceships;
    }

    public static Field adversaryField = new Field();
    public static Spaceship[] adversarySpaceships = createSpaceShipsForAdversary();

    private static Spaceship[] createSpaceShipsForAdversary() {
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
}
