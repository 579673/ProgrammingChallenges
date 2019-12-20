package com.codewars;

public class TheClockwiseSpiral {
    public static void main(String[] args) {
        printSpiral(createSpiral(25));
    }

    /**
     * Utility method for printing the spiral, making sure the array looks nice
     * @param spiral
     */
    private static void printSpiral(int[][] spiral) {
        int highestNumber = spiral.length * spiral.length;
        int highestNumberOfDigits = String.valueOf(highestNumber).length();
        System.out.println("HighestNumber is " + highestNumberOfDigits);

        for (int[] line : spiral) {
            System.out.print("[");
            for (int i : line) {
                System.out.printf("%" + highestNumberOfDigits + "d, ", i);
            }
            System.out.print("]\n");
        }
    }

    /**
     * Return a NxN array with the numbers 1 to N*N spiraled inwards.
     * @param N array length
     * @return spiral array
     */
    public static int[][] createSpiral(int N) {
        int[][] spiral = new int[N][N];
        int counter = 1;
        int x = 0, y = 0;
        int[] direction = {1, 0};

        while (counter <= N * N) {
            spiral[y][x] = counter;
            counter++;

            int nextX = x + direction[0];
            int nextY = y + direction[1];

            if (nextX >= N || nextX < 0 || nextY >= N || nextY < 0 || spiral[nextY][nextX] != 0) {
                rotate(direction);
            }
            x += direction[0];
            y += direction[1];
        }
        return spiral;
    }

    /**
     * Utility method for rotating a 2d vector
     * @param vector array with 2 values
     */
    private static void rotate(int[] vector) {
        double theta = Math.toRadians(90);
        int newX = (int)(Math.cos(theta) * vector[0] - Math.sin(theta) * vector[1]);
        int newY = (int)(Math.sin(theta) * vector[0] + Math.cos(theta) * vector[1]);
        vector[0] = newX;
        vector[1] = newY;
    }
}
