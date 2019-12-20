package com.codewars;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class HowManyNumbers {
    public static void main(String[] args) {
        findAll(10, 3).forEach(System.out::println);
    }
    public static List<Long> findAll(final int sumDigits, final int numDigits) {
        long start = Math.round(Math.pow(10, numDigits-1));
        long end = Math.round(Math.pow(10, numDigits));
        var stats = LongStream.range(start, end)
                        .filter(x -> {
                            int[] digits = getDigits(x);
                            return digitsAreIncreasing(digits) && sumOfDigitsIsCorrect(digits, sumDigits);
                        })
                        .summaryStatistics();
        if (stats.getCount() > 0) {
            return List.of(stats.getCount(), stats.getMin(), stats.getMax());
        }
        else {
            return List.of();
        }
    }
    public static void test(final int sumDigits, final int numDigits) {
        int lowestDigit = findLowestViableDigit(sumDigits, numDigits);
        long start = findFirstViableNumber(lowestDigit, sumDigits, numDigits);
        long end = findLastViableNumber(lowestDigit, sumDigits, numDigits);
        long[] allViableNumbers = findAllViableNumbers(lowestDigit, start, end, sumDigits, numDigits);

    }
    private static long incrementToNextViableNumber(long number, int sumDigits) {
        int[] digits = getDigits(number);
        boolean found = false;
        int index = digits.length-2;
        while (!found) {

        }
        return 1;
    }
    private static int findLowestViableDigit(int sumDigits, int numDigits) {

        return 1;
    }
    private static long findFirstViableNumber(int lowestDigit, int sumDigits, int numDigits) {

        return 1;
    }
    private static long findLastViableNumber(int lowestDigit, int sumDigits, int numDigits) {

        return 1;
    }
    private static long[] findAllViableNumbers(int lowestDigit, long start, long end, int sumDigits, int numDigits) {

        return null;
    }
    private static int[] getDigits(long x) {
        int numDigits = (int)Math.log10(x);
        int[] digits = new int[numDigits];
        for(int i = 0; i < numDigits; i++) {
            long div = Math.round(Math.pow(10, numDigits - 1 - i));
            digits[i] = Math.toIntExact(x / div);
            x = x % div;
        }
        return digits;
    }
    private static boolean digitsAreIncreasing(int[] digits) {
        int prev = 0;
        for (int digit : digits) {
            if (digit < prev) {
                return false;
            }
            prev = digit;
        }
        return true;
    }
    private static boolean sumOfDigitsIsCorrect(int[] digits, int sumDigits) {
        return IntStream.of(digits).sum() == sumDigits;
    }
}
