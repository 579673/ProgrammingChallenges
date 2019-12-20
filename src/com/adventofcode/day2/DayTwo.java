package com.adventofcode.day2;

import java.util.Arrays;

public class DayTwo {
    private final static int[] intCodeProgram = {1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,19,10,23,1,23,6,27,1,6,27,31,1,13,
            31,35,1, 13,35,39,1,39,13,43,2,43,9,47,2,6,47,51,1,51,9,55,1,55,9,59,1,59,6,63,1,9,63,67,2,67,10,71,2,71,13,
            75, 1,10,75,79,2,10,79,83,1,83,6,87,2,87,10,91,1,91,6,95,1,95,13,99,1,99,13,103,2,103,9,107,2,107,10,111,1,
            5,111,115,2,115,9,119,1,5,119,123,1,123,9,127,1,127,2,131,1,5,131,0,99,2,0,14,0};

    private final static int[] modifiedIntCodeProgram = {1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,19,10,23,1,23,6,27,1,6,27,
            31,1,13,31,35,1, 13,35,39,1,39,13,43,2,43,9,47,2,6,47,51,1,51,9,55,1,55,9,59,1,59,6,63,1,9,63,67,2,67,10,71,
            2,71,13,75, 1,10,75,79,2,10,79,83,1,83,6,87,2,87,10,91,1,91,6,95,1,95,13,99,1,99,13,103,2,103,9,107,2,107,10,
            111,1,5,111,115,2,115,9,119,1,5,119,123,1,123,9,127,1,127,2,131,1,5,131,0,99,2,0,14,0};

    public static void main(String[] args) throws Exception {
        int[] result = IntcodeComputer.parseIntcode(modifiedIntCodeProgram);
        System.out.println(Arrays.toString(result));

        // Part day2:
        int[] correctInputs = findInputs(intCodeProgram, 19690720);
        if (correctInputs != null && correctInputs.length == 2) {
            System.out.println("The input noun is: " + correctInputs[0] + ", the verb is: " + correctInputs[1]);
        } else {
            System.out.println("Could not find any inputs which would return to that value");
        }
    }
    private static int[] findInputs(final int[] inputCode, final int valueToLookFor) throws Exception {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int[] intCode = new int[inputCode.length];
                System.arraycopy(inputCode, 0, intCode, 0, intCode.length);
                intCode[1] = noun;
                intCode[2] = verb;
                int[] result = IntcodeComputer.parseIntcode(intCode);
                if (result[0] == valueToLookFor) {
                    return new int[] {noun, verb};
                }
            }
        }
        return null;
    }
}
