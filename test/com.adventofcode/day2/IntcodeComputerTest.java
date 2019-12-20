package com.adventofcode.day2;

import com.adventofcode.day2.exceptions.InvalidOpcodeException;
import com.adventofcode.day2.exceptions.InvalidModeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IntcodeComputerTest {
    int[] jumpFalseTestInput = {1106, 0, 4, 99, 1101, 2, 2, 0};
    int[] jumpFalseTestInputExpected = {4, 0, 4, 99, 1101, 2, 2, 0};

    int[] lessThanTestInput = {7, 1, 2, 0};
    int[] lessThanTestInputExpected = {1, 1, 2, 0};

    int[] equalsTestInput = {8, 0, 0, 3};
    int[] equalsTestInputExpected = {8, 0, 0, 1};

    @Test
    void jumpIfFalseTest() {
        try {
            assertArrayEquals(jumpFalseTestInputExpected, IntcodeComputer.parseIntcode(jumpFalseTestInput));
        } catch (InvalidOpcodeException e) {
            e.printStackTrace();
        } catch (InvalidModeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void lessThanTest() {
        try {
            assertArrayEquals(lessThanTestInputExpected, IntcodeComputer.parseIntcode(lessThanTestInput));
        } catch (InvalidOpcodeException e) {
            e.printStackTrace();
        } catch (InvalidModeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void equalsTest() {
        try {
            assertArrayEquals(equalsTestInputExpected, IntcodeComputer.parseIntcode(equalsTestInput));
        } catch (InvalidOpcodeException e) {
            e.printStackTrace();
        } catch (InvalidModeException e) {
            e.printStackTrace();
        }
    }
}
