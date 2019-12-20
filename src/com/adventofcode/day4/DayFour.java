package com.adventofcode.day4;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class DayFour {
    public static void main(String[] args) {
        Supplier<IntStream> partOne = () -> IntStream.iterate(156217, x -> x <= 652527, x -> x+1)
                .filter(DayFour::digitsIncreasing)
                .filter(DayFour::hasTwoEqualAdjDigits);
        long partOneCount = partOne.get().count();

        long partTwoCount = partOne.get().filter(DayFour::noMoreThanTwoRepeats).count();

        System.out.println("Part One count: " + partOneCount + "\nPart Two count: " + partTwoCount);
    }
    private static boolean noMoreThanTwoRepeats(int n) {
        Map<Character, Integer> digitCounts = new HashMap<>();
        for (char c : String.valueOf(n).toCharArray()) {
            if (digitCounts.containsKey(c)) {
                int newValue = digitCounts.get(c) + 1;
                digitCounts.put(c, newValue);
            } else {
                digitCounts.put(c, 1);
            }
        }
        return digitCounts.containsValue(2);
    }
    private static boolean digitsIncreasing(int n) {
        char prev = '0' - 1;
        for (char digit : String.valueOf(n).toCharArray()) {
            if (digit < prev) {
                return false;
            }
            prev = digit;
        }
        return true;
    }
    private static boolean hasTwoEqualAdjDigits(int n) {
        char prev = '0' - 1;
        for (char digit : String.valueOf(n).toCharArray()) {
            if (digit == prev) {
                return true;
            }
            prev = digit;
        }
        return false;
    }
}
