package com.codewars;

import java.awt.*;
import java.util.Scanner;

// INCOMPLETE
public class TwentyFortyEight {
    final static Point NORTH = new Point(0, -1);
    final static Point SOUTH = new Point(0, 1);
    final static Point EAST = new Point(1, 0);
    final static Point WEST = new Point(-1, 0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        while(scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.equals("w")) {
                board.moveAllCells(NORTH);
            }
            if (input.equals("a")) {
                board.moveAllCells(WEST);
            }
            if (input.equals("s")) {
                board.moveAllCells(SOUTH);
            }
            if (input.equals("d")) {
                board.moveAllCells(EAST);
            }
            if (input.equals("q")) {
                break;
            }
            board.setCell(0, 0, 2);
            board.drawBoard();
        }
    }
}
class Board {
    int[][] board = new int[4][4];

    public void setCell(int x, int y, int value) {
        board[y][x] = value;
    }

    public void moveAllCells2(int direction) {
        if (direction == Directions.NORTH || direction == Directions.SOUTH) {
           // moveVertical(direction);
        }
        else {
            moveHorizontal(direction);
        }
    }

    private void moveHorizontal(int direction) {
        if (direction == Directions.EAST) {
            for (int[] row : board) {

            }
        }
    }

    public void moveAllCells(Point direction) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] != 0) {
                    moveAndMergeCell(x, y, direction);
                }
            }
        }
    }
    private void moveAndMergeCell(int x, int y, Point direction) {
        while(notOutOfBounds(x, y)) {
            int nextX = x + direction.x;
            int nextY = y + direction.y;
            if (notOutOfBounds(nextX, nextY)) {
                if (board[nextY][nextX] == 0) {
                    board[nextY][nextX] = board[y][x];
                    board[y][x] = 0;
                } else if (board[nextY][nextX] == board[y][x]) {
                    board[nextY][nextX] += board[y][x];
                    board[y][x] = 0;
                }
            }
            x = nextX;
            y = nextY;
        }
    }
    private boolean notOutOfBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < board[0].length && y < board.length;
    }

    public void drawBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.printf("%3s", cell);
            }
            System.out.println();
        }
    }
}
class Directions {
    final static int NORTH = 0;
    final static int EAST  = 1;
    final static int SOUTH = 2;
    final static int WEST  = 3;
}