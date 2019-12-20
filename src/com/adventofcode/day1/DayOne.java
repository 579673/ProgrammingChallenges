package com.adventofcode.day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DayOne {
    public static void main(String[] args) {
        int[] modules = readNumbersFromFile();
        int fuelRequiredForModules = Arrays.stream(modules).map(DayOne::calculateFuel).sum();
        System.out.println("Fuel required for modules: " + fuelRequiredForModules);
        int fuelRequiredInTotal = calculateTotalFuel(modules);
        System.out.println("Fuel required when taking additional fuel into account: \n" + fuelRequiredInTotal);
    }
    private static int calculateTotalFuel(int[] modules) {
        int sum = 0;
        for (int module : modules) {
            sum += calculateModuleFuel(module);
        }
        return sum;
    }
    private static int calculateModuleFuel(int module) {
        int fuel = calculateFuel(module);
        int previousFuel = fuel;

        for (int additionalFuel = calculateFuel(previousFuel);
                 additionalFuel > 0;
                 additionalFuel = calculateFuel(previousFuel)) {

            fuel += additionalFuel;
            previousFuel = additionalFuel;
        }
        return fuel;
    }
    private static int[] readNumbersFromFile() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                        new File("resources/dayOneInput.txt")))) {
            return reader.lines().mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return null;
    }
    private static int calculateFuel(int mass) {
        return mass / 3 - 2;
    }
}
