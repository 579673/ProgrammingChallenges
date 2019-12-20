package com.codewars;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TopWords {

    /**
     * Returns a list of the top 3 most frequently occurring words
     * in input string.
     * @param s input string
     * @return  top 3 List
     */
    public static List<String> top3(String s) {
        return Arrays.stream(s.toLowerCase().split("[^a-z']"))
                .filter(str -> str.matches("[a-z]*'*[a-z]+'*[a-z]*"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        top3("e A A A A B B B C C D").forEach(System.out::println);
    }
}
