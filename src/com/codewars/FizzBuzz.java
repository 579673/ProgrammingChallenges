package com.codewars;

public class FizzBuzz {
    public static void main(String[] args) {
        fizzBuzz(30);
    }
    public static void fizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0) {
                System.out.print("fizz");
            }
            if (i % 5 == 0) {
                System.out.print("buzz");
            }
            if (i % 3 != 0 && i % 5 != 0) {
                System.out.print(i);
            }
            System.out.print(", ");
        }
    }
}
