package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;

import com.example.myapplication.Field;

public class Spaceship {
    public int id;
    public int cellCount;
    public double startX;
    public double startY;
    public double currentX;
    public double currentY;
    double size;
    public com.example.myapplication.SpaceshipState state;

    public Spaceship(int id, int cellCount) {
        this.id = id;
        this.cellCount = cellCount;
    }

    public Spaceship(int id, int cellCount, double startX, double startY) {
        this.id = id;
        this.cellCount = cellCount;
        this.startX = startX;
        this.startY = startY;
    }

    public void draw(double size, Canvas canvas, double x, double y) {
        this.size = size;
        currentX = x;
        currentY = y;
        Paint p = new Paint();
        p.setARGB(255, 3, 218, 197);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect((float) x, (float) y, (float) calculateRightX(x), (float) calculateBottomY(y), p);

        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect((float) x, (float) y, (float) calculateRightX(x), (float) calculateBottomY(y), p);
    }

    public boolean isPointInSpaceship (double x, double y) {
        return (x >= currentX) && (x <= calculateRightX(currentX)) && (y >= currentY) && (y <= calculateBottomY(currentY));
    }

    private double calculateRightX(double x){
        return x + cellCount * size;
    }

    private double calculateBottomY(double y){
        return y + size;
    }

    /*public boolean takeSpaceShipInField(int x, int y, Field field) {
        boolean res = takeSpaceShipInField1(0, 0, field);
        res = takeSpaceShipInField1(2, 3, field);
        res = takeSpaceShipInField1(0, 0, field);
    }*/

    public boolean takeSpaceShipInField(int x, int y, Field field) {
        boolean isErrorCell = false;
        int errorCell = 0;
        for (int i = 0; i < cellCount; i++, x++, errorCell++) {
            if (x >= field.cells.length || y >= field.cells.length || field.cells[x][y].idSpaceShip != 0) {
                isErrorCell = true;
                break;
            } else {
                field.cells[x][y].idSpaceShip = id;
            }

            if (x - 1 >= 0) {
                if (field.cells[x - 1][y].idSpaceShip != 0 && field.cells[x - 1][y].idSpaceShip != field.cells[x][y].idSpaceShip) {
                    isErrorCell = true;
                    break;
                }
                if (y - 1 >= 0) {
                    if (field.cells[x - 1][y - 1].idSpaceShip != 0) {
                        isErrorCell = true;
                        break;
                    }
                }
                if (y + 1 <= 9) {
                    if (field.cells[x - 1][y + 1].idSpaceShip != 0) {
                        isErrorCell = true;
                        break;
                    }
                }
            }

            if (x + 1 <= 9) {
                if (field.cells[x + 1][y].idSpaceShip != 0 && field.cells[x + 1][y].idSpaceShip != field.cells[x][y].idSpaceShip) {
                    isErrorCell = true;
                    break;
                }
                if (y - 1 >= 0) {
                    if (field.cells[x + 1][y - 1].idSpaceShip != 0) {
                        isErrorCell = true;
                        break;
                    }
                }
                if (y + 1 <= 9) {
                    if (field.cells[x + 1][y + 1].idSpaceShip != 0) {
                        isErrorCell = true;
                        break;
                    }
                }
            }

            if (y - 1 >= 0) {
                if (field.cells[x][y - 1].idSpaceShip != 0 && field.cells[x][y - 1].idSpaceShip != field.cells[x][y].idSpaceShip) {
                    isErrorCell = true;
                    break;
                }
            }

            if (y + 1 <= 9) {
                if (field.cells[x][y + 1].idSpaceShip != 0 && field.cells[x][y + 1].idSpaceShip != field.cells[x][y].idSpaceShip) {
                    isErrorCell = true;
                    break;
                }
            }
        }

        if (isErrorCell) {
            x--;
            for (int i = 0; i < errorCell; i++, x--) {
                field.cells[x][y].idSpaceShip = 0;
            }
            return false;
        } else return true;
    }
}
