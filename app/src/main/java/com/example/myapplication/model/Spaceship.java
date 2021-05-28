package com.example.myapplication.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

// Класс корабля
public class Spaceship {
    public final int id;
    private final int cellCount;
    public double startX;
    public double startY;
    public double currentX;
    public double currentY;
    public int currentCellX = -1;
    public int currentCellY = -1;
    private double cellSize;
    public boolean isRotated = false; // = true, если корабль стоит вертикально, = false, если горизонтально
    public SpaceshipState state = SpaceshipState.NORMAL; // состояние (начальное, поставлен на поле, ранен, убит)
    public int damagedCells = 0; // количество поврежденных клеток корабля

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

    // Возращает количество клеток, занимаемых кораблем
    public int getCellCount() {
        return cellCount;
    }

    // Рисует корабль на канве, начиная с координаты (x,y)
    public void draw(double cellSize, Canvas canvas, double x, double y) {
        //Здесь баг
        this.cellSize = cellSize;
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

    // Возвращает true, если точка с координатами (x,y) принадлежит кораблю
    public boolean isPointInSpaceship (double x, double y) {
        return (x >= currentX) && (x <= calculateRightX(currentX)) && (y >= currentY) && (y <= calculateBottomY(currentY));
    }

    // Вычисляет конец корабля по X
    private double calculateRightX(double x){
        if (!isRotated) return x + cellCount * cellSize;
        else return x + cellSize;
    }

    // Вычисляет конец корабля по Y
    private double calculateBottomY(double y){
        if (!isRotated) return y + cellSize;
        else return y + cellCount * cellSize;
    }

    // Ищет клетки поля, в которых стоит этот корабль (если он уже был поставлен на поле)
    // Запоминает их в currentCells.
    private ArrayList<Cell> rememberOldPositions(Field field) {
        ArrayList<Cell> currentCells = new ArrayList<>();
        for (int ix = 0; ix < field.cells.length; ix++) {
            for (int iy = 0; iy < field.cells.length; iy++) {
                if (field.cells[ix][iy].idSpaceShip == id) {
                    currentCells.add(field.cells[ix][iy]);
                }
            }
        }
        return currentCells;
    }

    private void clearCells(ArrayList<Cell> currentCells) {
        for (Cell cell: currentCells) {
            if (cell.idSpaceShip == id) {
                cell.idSpaceShip = 0;
            }
        }
    }

    // Ставит корабль на поле
    public boolean takeSpaceShipInField(int x, int y, Field field) {
        boolean isErrorCell = false;
        int errorCell = 0;
        // находит, где корабль находился раньше (если он уже стоял на поле)
        ArrayList<Cell> currentCells = rememberOldPositions(field);

        // чистит старое расположение корабля
        clearCells(currentCells);

        //Пытается заполнить клетки поля собой. Если на корабль не входит в поле или он находится близко к другим кораблям
        //прерывает заполнение
        int j = isRotated ? y : x;
        for (int i = 0; i < cellCount; i++, j++, errorCell++) {
            boolean condition = false;
            int count = field.cells.length;
            if (!isRotated) {
                condition = j >= count || y >= count || field.cells[j][y].idSpaceShip != 0;
            } else {
                condition = j >= count || x >= count || field.cells[x][j].idSpaceShip != 0;
            }
            if (condition) {
                isErrorCell = true;
                break;
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

            if (isRotated) {
                field.cells[x][j].idSpaceShip = id;
            } else {
                field.cells[j][y].idSpaceShip = id;
            }
        }

        if (isErrorCell) {
            // чистит клетки, которые поставил
            j--;
            for (int i = 0; i < errorCell; i++, j--) {
               if (!isRotated) {
                   if (field.cells[j][y].idSpaceShip == id) {
                       field.cells[j][y].idSpaceShip = 0;
                   }
               } else {
                   if (field.cells[x][j].idSpaceShip == id) {
                       field.cells[x][j].idSpaceShip = 0;
                   }
               }
            }
            return false;
        } else {
            currentCellX = x;
            currentCellY = y;
            state = SpaceshipState.TAKEN;
            return true;
        }
    }

    private boolean checkIsValid(int x, int y, Field field) {
        if (x - 1 >= 0) {
            if (field.cells[x - 1][y].idSpaceShip != 0 && field.cells[x - 1][y].idSpaceShip != id) {
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
