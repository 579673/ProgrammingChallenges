package com.adventofcode.day2;

import com.adventofcode.day2.exceptions.InvalidOpcodeException;
import com.adventofcode.day2.exceptions.InvalidModeException;

import java.util.Scanner;


public class IntcodeComputer {
    private final static int ADD = 1;
    private final static int MUL = 2;
    private final static int IN = 3;
    private final static int OUT = 4;
    private final static int JT = 5;
    private final static int JF = 6;
    private final static int LT = 7;
    private final static int EQU = 8;
    private final static int HALT = 99;

    private final static int MODE_POSITION = 0;
    private final static int MODE_IMMEDIATE = 1;

    private final static int OUTPUT_MODE_CONSOLE = 0;
    private final static int OUTPUT_MODE_ARRAY = 1;
    private final static int INPUT_MODE_CONSOLE = 0;
    private final static int INPUT_MODE_ARRAY = 1;

    private static int[] parseInstruction(int instruction) {
        int[] result = new int[4];
        result[0] = instruction % 100;
        instruction /= 100;
        result[1] = instruction % 10;
        instruction /= 10;
        result[2] = instruction % 10;
        instruction /= 10;
        result[3] = instruction % 10;
        return result;
    }

    /**
     * Default mode, reads from and prints to the console.
     * @param inputProgram The intcode to be executed
     * @return Returns the altered intcode program
     * @throws InvalidModeException
     * @throws InvalidOpcodeException
     */
    public static int[] parseIntcode(int[] inputProgram) throws InvalidModeException, InvalidOpcodeException {
        return parseIntcode(inputProgram, INPUT_MODE_CONSOLE, OUTPUT_MODE_CONSOLE, null, null);
    }

    /**
     * Includes support for inputs and outputs by arrays
     * @param inputProgram The intcode to be executed
     * @param inputMode 0 is console, 1 is array
     * @param outputMode 0 is console, 1 is array
     * @param inputArray An array of inputs
     * @param outputArray An array for outputs
     * @return Returns the altered intcode program
     * @throws InvalidModeException
     * @throws InvalidOpcodeException
     */
    public static int[] parseIntcode(int[] inputProgram, int inputMode, int outputMode, int[] inputArray, int[] outputArray)
                                                        throws InvalidModeException, InvalidOpcodeException {
        int instructionLength;
        int inputCounter = 0;
        int outputCounter = 0;
        int[] intcode = new int[inputProgram.length];
        System.arraycopy(inputProgram, 0, intcode, 0, intcode.length);

        parsing:
        for (int i = 0; i < intcode.length; i += instructionLength) {
            int[] instruction = parseInstruction(intcode[i]);

            final int[] paramModes = { instruction[1], instruction[2], instruction[3] };
            if (invalidParamModes(paramModes)) {
                throw new InvalidModeException("Invalid parameter mode at index " + i
                        + " with instruction " + intcode[i]);
            }

            final int opcode = instruction[0];
            instructionLength = getInstructionLength(opcode);

            final int[] params = new int[instructionLength-1];

            for (int n = 0; n < params.length; n++) {
                params[n] = paramModes[n] == MODE_POSITION ? intcode[i+1+n] : i+1+n;
            }

            switch(opcode) {
                case HALT:
                    break parsing;
                case ADD:
                    intcode[params[2]] = intcode[params[0]] + intcode[params[1]];
                    break;
                case MUL:
                    intcode[params[2]] = intcode[params[0]] * intcode[params[1]];
                    break;
                case IN:
                    if (inputMode == INPUT_MODE_CONSOLE) {
                        System.out.print("Requesting integer input: ");
                        intcode[params[0]] = new Scanner(System.in).nextInt();
                    } else if (inputMode == INPUT_MODE_ARRAY) {
                        intcode[params[0]] = inputArray[inputCounter];
                        inputCounter++;
                    } else {
                        throw new InvalidModeException("Invalid input mode: " + inputMode);
                    }
                    break;
                case OUT:
                    if (outputMode == OUTPUT_MODE_CONSOLE) {
                        System.out.println(intcode[params[0]]);
                    } else if (outputMode == OUTPUT_MODE_ARRAY) {
                        outputArray[outputCounter] = intcode[params[0]];
                        outputCounter++;
                    } else {
                        throw new InvalidModeException("Invalid output mode: " + outputMode);
                    }
                    break;
                case JT:
                    if (intcode[params[0]] != 0) {
                        i = intcode[params[1]];
                        instructionLength = 0;
                    }
                    break;
                case JF:
                    if (intcode[params[0]] == 0) {
                        i = intcode[params[1]];
                        instructionLength = 0;
                    }
                    break;
                case LT:
                    if (intcode[params[0]] < intcode[params[1]]) {
                        intcode[params[2]] = 1;
                    } else {
                        intcode[params[2]] = 0;
                    }
                    break;
                case EQU:
                    intcode[params[2]] = intcode[params[0]] == intcode[params[1]] ? 1 : 0;
                    break;
                default:
                    throw new InvalidOpcodeException("Opcode was: " + opcode + " at index " + i
                            + " with instruction " + intcode[i]);
            }
        }
        return intcode;
    }

    private static int getInstructionLength(int opcode) {
        if (opcode == IN || opcode == OUT) {
            return 2;
        } else if (opcode == JF || opcode == JT) {
            return 3;
        } else if (opcode == HALT) {
            return 1;
        }
        return 4;
    }

    private static boolean invalidParamModes(int[] paramModes) {
        for (int paramMode : paramModes) {
            if (!(paramMode == MODE_POSITION || paramMode == MODE_IMMEDIATE)) {
                return true;
            }
        }
        return false;
    }
}
