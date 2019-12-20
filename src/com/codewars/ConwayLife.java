package com.codewars;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ConwayLife {
    public static void main(String[] args) {
        int[][][] gliders = {
                {       {1,0,0},
                        {0,1,1},
                        {1,1,0}
                        },
                {       {0,1,0},
                        {0,0,1},
                        {1,1,1}
                }
        };
        for (int[] line : getGeneration(gliders[0], 1)) {
            System.out.println(Arrays.toString(line));
        }
    }

    public static int[][] getGeneration(int[][] cells, int generations) {
        //Initialize list of cells
        Map<Point, Cell> livingCells = new HashMap<>();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                if (cells[y][x] == 1) {
                    Point pos = new Point(x, y);
                    livingCells.put(pos, new Cell(pos));
                }
            }
        }

        //Run through the generations
        for (int gen = 0; gen < generations; gen++) {
            livingCells = computeGeneration(livingCells);
        }

        //Convert back to array
        return convertToArray(livingCells);
    }
    private static int[][] convertToArray(Map<Point, Cell> cells) {
        //Find min/max points
        int xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE, xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE;

        for (Point p : cells.keySet()) {
            xMax = p.x > xMax ? p.x : xMax;
            xMin = p.x < xMin ? p.x : xMin;
            yMax = p.y > yMax ? p.y : yMax;
            yMin = p.y < yMin ? p.y : yMin;
        }
        int xRange = xMax - xMin;
        int yRange = yMax - yMin;
        int xOffset = xMin;
        int yOffset = yMin;

        int[][] arr = new int[yRange+1][xRange+1];
        for (Point p : cells.keySet()) {
            arr[p.y - yOffset][p.x - xOffset] = 1;
        }
        return arr;
    }
    private static Map<Point, Cell> computeGeneration(Map<Point, Cell> livingCells) {
        Map<Point, Cell> newLivingCells = new HashMap<>();
        Set<Cell> deadNeighbours = new HashSet<>();

        for (Cell c : livingCells.values()) {
            //Add cell if it lives on to the next generation
            int liveNeighbours = c.countLivingNeighbours(livingCells);
            if (liveNeighbours == 2 || liveNeighbours == 3) {
                newLivingCells.put(c.getPos(), c);
            }

            //Add neighbours to the set of dead neighbours, if they are dead
            for (Point p : c.getNeighbours()) {
                if (!livingCells.containsKey(p)) {
                    deadNeighbours.add(new Cell(p));
                }
            }
        }
        //Add cells if they come alive
        for (Cell c : deadNeighbours) {
            if (c.countLivingNeighbours(livingCells) == 3) {
                newLivingCells.put(c.getPos(), c);
            }
        }

        return newLivingCells;
    }
}

class Cell {
    private Point pos;

    public Cell(Point pos) {
        this.pos = pos;
    }

    public int countLivingNeighbours(Map<Point, Cell> livingCells) {
        int count = 0;
        for (Point p : getNeighbours()) {
            if (livingCells.containsKey(p)) {
                count++;
            }
        }
        return count;
    }

    public List<Point> getNeighbours() {
        int x = pos.x;
        int y = pos.y;
        return List.of(
                new Point(x+1, y),
                new Point(x, y+1),
                new Point(x-1, y),
                new Point(x, y-1),
                new Point(x+1, y+1),
                new Point(x-1, y-1),
                new Point(x+1, y-1),
                new Point(x-1, y+1)
        );
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell)) {
            return false;
        }
        Cell c = (Cell)o;
        return this.getPos() == c.getPos();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
