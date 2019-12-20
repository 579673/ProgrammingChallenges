package com.adventofcode.day2;

import com.adventofcode.day2.exceptions.InvalidOpcodeException;
import com.adventofcode.day2.exceptions.InvalidParameterModeException;

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

    public static int[] parseIntcode(int[] input) throws InvalidParameterModeException, InvalidOpcodeException {
        int instructionLength;
        int[] intcode = new int[input.length];
        System.arraycopy(input, 0, intcode, 0, intcode.length);

        parsing:
        for (int i = 0; i < intcode.length; i += instructionLength) {
            int[] instruction = parseInstruction(intcode[i]);

            final int[] paramModes = { instruction[1], instruction[2], instruction[3] };
            if (invalidParamModes(paramModes)) {
                throw new InvalidParameterModeException("Invalid parameter mode at index " + i
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
                    System.out.print("Requesting integer input: ");
                    intcode[params[0]] = new Scanner(System.in).nextInt();
                    break;
                case OUT:
                    System.out.println(intcode[params[0]]);
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
