package com.example.myapplication.model;

public class Player {
    public int id;
    public String name;
    public int victories;
    public int losses;
    public int vsProgramVictories;
    public int vsProgramLosses;

    public int levelExperience() {
        int sum = victories + losses;
        double coefficient = 0;
        if (losses == 0)
            coefficient = 1;
        else
            coefficient = victories / losses;

        if (sum * coefficient / 10 > 1)
        return (int) (sum * coefficient / 10);
        else return 1;
    }
}
