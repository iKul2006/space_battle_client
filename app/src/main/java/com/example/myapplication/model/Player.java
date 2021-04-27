package com.example.myapplication;

public class Player {
    public int id;
    public String name;
    public int victories;
    public int losses;

    public int levelExperience() {
        int a = victories + losses;
        int b = 0;
        if (losses == 0)
            b = 100;
        else
            b = 100 / a * victories;

        if (a * b > 1)
        return a * b;
        else return 1;
    }
}
