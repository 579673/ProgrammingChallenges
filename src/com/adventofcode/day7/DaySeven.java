package com.adventofcode.day7;

import java.util.Arrays;

public class DaySeven {
    static final int[] intcodeProgram = {3,8,1001,8,10,8,105,1,0,0,21,38,63,72,81,106,187,268,349,430,99999,3,9,101,5,9,9,1002,
            9,3,9,101,3,9,9,4,9,99,3,9,102,3,9,9,101,4,9,9,1002,9,2,9,1001,9,2,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,4,9,99,
            3,9,102,5,9,9,4,9,99,3,9,102,4,9,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,102,3,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,
            1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,
            3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,
            9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,
            1,9,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,
            9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,
            9,99,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,
            1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,
            9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,
            3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99};

    public static void main(String[] args) throws Exception {
        int[][] phaseSettingOrders = generateAllPhaseSettingPermutations(0, 1, 2, 3, 4);

        int[] bestOrder = null;
        int bestAmplification = 0;
        for (int[] row : phaseSettingOrders) {
            AmplificationCircuit circuit = new AmplificationCircuit(intcodeProgram, row);
            int currentAmplification = circuit.startAmplification(0);
            if (currentAmplification > bestAmplification) {
                bestAmplification = currentAmplification;
                bestOrder = row;
            }
        }
        System.out.println("The best combination is " + Arrays.toString(bestOrder) + " with a value of " + bestAmplification);

        // Part two:
        int[][] phaseSettingOrdersPartTwo = generateAllPhaseSettingPermutations(5, 6, 7, 8, 9);
        int[] bestOrderPartTwo = null;
        int bestAmplificationPartTwo = 0;

        for (int[] row : phaseSettingOrdersPartTwo) {
            AmplificationCircuit circuit = new AmplificationCircuit(intcodeProgram, row);
            circuit.initiateFeedbackLoop();
            int currentAmplification = circuit.startAmplification(0);
            if (currentAmplification > bestAmplificationPartTwo) {
                bestAmplificationPartTwo = currentAmplification;
                bestOrderPartTwo = row;
            }
        }

        System.out.println("The best feedback loop combination is " +
                Arrays.toString(bestOrderPartTwo) + " with a value of " + bestAmplificationPartTwo);
    }

    private static int[][] generateAllPhaseSettingPermutations(int... phaseSettingSeed) {
        int n = phaseSettingSeed.length;
        int[][] result = new int[factorial(n)][];
        int[] indexes = new int[n];
        int resultIndex = 0;

        result[resultIndex] = Arrays.copyOf(phaseSettingSeed, n);
        resultIndex++;

        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(phaseSettingSeed, i % 2 == 0 ? 0 : indexes[i], i);
                result[resultIndex] = Arrays.copyOf(phaseSettingSeed, n);
                resultIndex++;
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }

        return result;
    }

    private static void swap(int[] arr, int indexA, int indexB) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }

    private static int factorial(int n) {
        if (n > 0) {
            return n * factorial(n-1);
        }
        return 1;
    }
}
