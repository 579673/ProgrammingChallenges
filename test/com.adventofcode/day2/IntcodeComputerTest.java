package com.adventofcode.day2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IntcodeComputerTest {
    IntcodeComputer computer = new IntcodeComputer(null);
    int[] jumpFalseTestInput = {1106, 0, 4, 99, 1101, 2, 2, 0};
    int[] jumpFalseTestInputExpected = {4, 0, 4, 99, 1101, 2, 2, 0};

    int[] lessThanTestInput = {7, 1, 2, 0};
    int[] lessThanTestInputExpected = {1, 1, 2, 0};

    int[] equalsTestInput = {8, 0, 0, 3};
    int[] equalsTestInputExpected = {8, 0, 0, 1};

    @Test
    void jumpIfFalseTest() {
        computer.setProgram(jumpFalseTestInput);
        assertArrayEquals(jumpFalseTestInputExpected, computer.run());
    }

    @Test
    void lessThanTest() {
            assertArrayEquals(lessThanTestInputExpected, computer.parseIntcode(lessThanTestInput));
    }

    @Test
    void equalsTest() {
            assertArrayEquals(equalsTestInputExpected, computer.parseIntcode(equalsTestInput));
    }
}
