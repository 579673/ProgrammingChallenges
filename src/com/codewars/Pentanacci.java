package com.codewars;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Pentanacci {
    /**
     * Counts the amount of odd numbers in the pentabonacci line up to n
     * @param n
     * @return
     */
    public static long countOddPentaFib(long n) {
        long[] lastFive = {0, 1, 1, 2, 4};
        long count = 0;
        long odds = 0;
        while (count < n){
            if (lastFive[0] % 2 != 0) {
                odds++;
            }
            long next = sum(lastFive);
            lastFive[0] = lastFive[1];
            lastFive[1] = lastFive[2];
            lastFive[2] = lastFive[3];
            lastFive[4] = next;
            count++;
        }
        return odds;
    }
    public static long sum(long[] arr) {
        long sum = 0;
        for (long l : arr) {
            sum += l;
        }
        return sum;
    }
    public static long countOddPentaFibStream(long n) {
        return Stream.iterate(new long[] {0, 1, 1, 2, 4}, i ->
                new long[] {i[1], i[2], i[3], i[4], i[0]+i[1]+i[2]+i[3]+i[4]})
                .limit(n)
                .map(i -> i[0])
                .filter(x -> x % 2 == 0)
                .count();
    }

    public static void main(String[] args) {
        System.out.println(countOddPentaFib(100));
        System.out.println(countOddPentaFibStream(100));
    }
}
