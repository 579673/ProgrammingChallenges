package com.codewars;

public class CalculateWeekDay {
    public static void main(String[] args) {
        System.out.println(whichDayIsToday(23, 11, 2019));
    }
    // 0 = Sat, 1 = Sun, 2 = Mon, 3 = Tues ... 6 = Fri
    static int whichDayIsToday(int d, int m, int y){
        double q = d;
        double a = (13 * (m + 1)) / 5d;
        double K = y % 100;
        double b = K / 4d;
        double j = y / 100d;
        double c = (j / 4d) - 2 * j;
        return (int)(q + a + K + b + c) % 7;
    }
}
