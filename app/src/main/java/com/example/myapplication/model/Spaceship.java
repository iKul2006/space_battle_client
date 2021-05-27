package com.example.myapplication.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;

import com.example.myapplication.model.Field;
import com.example.myapplication.model.Cell;
import com.example.myapplication.model.SpaceshipState;

import java.util.ArrayList;

public class Spaceship {
    public int id;
    public int cellCount;
    public double startX;
    public double startY;
    public double currentX;
    public double currentY;
    public int currentCellX = -1;
    public int currentCellY = -1;
    double size;
    public boolean isTaken = false;
    public boolean isRotated = false;
    public SpaceshipState state = SpaceshipState.NORMAL;
    public int damagedCells = 0;

    //Конструктор для расстановки кораблей соперником
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
        //Здесь баг
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

    //Вычисляет конец корабля по X
    private double calculateRightX(double x){
        if (!isRotated) return x + cellCount * size;
        else return x + size;
    }

    //Вычисляет конец корабля по Y
    private double calculateBottomY(double y){
        if (!isRotated) return y + size;
        else return y + cellCount * size;
    }

    public boolean takeSpaceShipInField(int x, int y, Field field) {
        boolean isErrorCell = false;
        int errorCell = 0;
        //Ищет клетки поля, в которых стоит этот корабль (если он уже был поставлен на поле)
        //Запоминает их в currentCells.
        ArrayList<Cell> currentCells = new ArrayList<>();
        for (int ix = 0; ix < field.cells.length; ix++) {
            for (int iy = 0; iy < field.cells.length; iy++) {
                if (field.cells[ix][iy].idSpaceShip == id) {
                    currentCells.add(field.cells[ix][iy]);
                }
            }
        }
        if (currentCellX == x && currentCellY == y){
            currentCells.remove(field.cells[x][y]);
        }

        //Пытается заполнить клетки поля собой. Если на корабль не входит в поле или он находится близко к другим кораблям
        //прерывает заполнение
        int j = isRotated ? y : x;
        for (int i = 0; i < cellCount; i++, j++, errorCell++) {
            boolean condition = false;
            if (!isRotated) {
                condition = j >= field.cells.length || y >= field.cells.length
                        || (field.cells[j][y].idSpaceShip != 0 && field.cells[j][y].idSpaceShip != id);
            } else {
                condition = j >= field.cells.length || x >= field.cells.length
                        || (field.cells[x][j].idSpaceShip != 0 && field.cells[x][j].idSpaceShip != id);
            }
            if (condition) {
                isErrorCell = true;
                break;
            } else {
                if (isRotated) {
                    field.cells[x][j].idSpaceShip = id;
                } else {
                    field.cells[j][y].idSpaceShip = id;
                }
            }

            //Проверяет, касается ли клетка кораблей.
            if (!isRotated) {
                isErrorCell = checkIsValid(j, y, field);
            } else {
                isErrorCell = checkIsValid(x, j, field);
            }
            if (isErrorCell) {
                break;
            }
        }

        if (isErrorCell) {
            j--;
            for (int i = 0; i < errorCell; i++, j--) {
               if (!isRotated) {
                   field.cells[j][y].idSpaceShip = 0;
               } else {
                   field.cells[x][j].idSpaceShip = 0;
               }
            }
            return false;
        } else {
            currentCellX = x;
            currentCellY = y;
            for (Cell cell: currentCells) {
                cell.idSpaceShip = 0;
            }
            isTaken = true;
            return true;
        }
    }

    private boolean checkIsValid(int x, int y, Field field) {
        if (x - 1 >= 0) {
            if (field.cells[x - 1][y].idSpaceShip != 0 && field.cells[x - 1][y].idSpaceShip != field.cells[x][y].idSpaceShip && !isRotated) {
                return true;
            }
            if (y - 1 >= 0) {
                if (field.cells[x - 1][y - 1].idSpaceShip != 0 && field.cells[x - 1][y - 1].idSpaceShip != id) {
                    return true;
                }
            }
            if (y + 1 <= 9) {
                if (field.cells[x - 1][y + 1].idSpaceShip != 0 && field.cells[x - 1][y + 1].idSpaceShip != id) {
                    return true;
                }
            }
        }

        if (x + 1 <= 9) {
            if (field.cells[x + 1][y].idSpaceShip != 0 && field.cells[x + 1][y].idSpaceShip != id) {
                return true;
            }
            if (y - 1 >= 0) {
                if (field.cells[x + 1][y - 1].idSpaceShip != 0 && field.cells[x + 1][y - 1].idSpaceShip != id) {
                    return true;
                }
            }
            if (y + 1 <= 9) {
                if (field.cells[x + 1][y + 1].idSpaceShip != 0 && field.cells[x + 1][y + 1].idSpaceShip != id) {
                    return true;
                }
            }
        }

        if (y - 1 >= 0) {
            if (field.cells[x][y - 1].idSpaceShip != 0 && field.cells[x][y - 1].idSpaceShip != id && isRotated) {
                return true;
            }
        }

        if (y + 1 <= 9) {
            if (field.cells[x][y + 1].idSpaceShip != 0 && field.cells[x][y + 1].idSpaceShip != id) {
                return true;
            }
        }

        return false;
    }

    public void rotateSpaceship (Field field) {
        isRotated = !isRotated;
        if(!takeSpaceShipInField(currentCellX, currentCellY, field)){
            isRotated = !isRotated;
        }
    }

    public void damage(){
        damagedCells++;
        if (damagedCells == cellCount){
            state = SpaceshipState.KILLED;
        } else {
            state = SpaceshipState.DAMAGED;
        }
    }
}
