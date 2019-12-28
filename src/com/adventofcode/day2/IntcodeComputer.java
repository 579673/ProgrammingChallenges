package com.adventofcode.day2;

import com.adventofcode.day2.exceptions.InvalidOpcodeException;
import com.adventofcode.day2.exceptions.InvalidModeException;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class IntcodeComputer implements Runnable {

    /* Constants */
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

    public final static int OUTPUT_MODE_CONSOLE = 0;
    public final static int OUTPUT_MODE_QUEUE = 1;
    public final static int INPUT_MODE_CONSOLE = 0;
    public final static int INPUT_MODE_QUEUE = 1;

    /* Instance variables */
    private int inputMode;
    private int outputMode;
    private BlockingQueue<Integer> inputQueue;
    private BlockingQueue<Integer> outputQueue;
    private int[] program;

    public IntcodeComputer(int[] program) {
        inputMode = INPUT_MODE_CONSOLE;
        outputMode = OUTPUT_MODE_CONSOLE;
        inputQueue = null;
        outputQueue = null;
        this.program = program;
    }

    public IntcodeComputer(int[] program, int inputMode, int outputMode,
                           BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue) {
        this.program = program;
        this.inputMode = inputMode;
        this.outputMode = outputMode;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public IntcodeComputer(int[] program, int inputMode, int outputMode) {
        this.program = program;
        this.inputMode = inputMode;
        this.outputMode = outputMode;
        this.inputQueue = new ArrayBlockingQueue<>(10, true);
        this.outputQueue = new ArrayBlockingQueue<>(10, true);
    }

    private int[] parseInstruction(int instruction) {
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
     * Runs the computer
     */
    public void run() {
        int instructionLength;
        int[] intcode = new int[program.length];
        System.arraycopy(program, 0, intcode, 0, intcode.length);

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
                    } else if (inputMode == INPUT_MODE_QUEUE) {
                        intcode[params[0]] = inputQueue.poll();
                    } else {
                        throw new InvalidModeException("Invalid input mode: " + inputMode);
                    }
                    break;
                case OUT:
                    if (outputMode == OUTPUT_MODE_CONSOLE) {
                        System.out.println(intcode[params[0]]);
                    } else if (outputMode == OUTPUT_MODE_QUEUE) {
                        outputQueue.add(intcode[params[0]]);
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
    }

    private int getInstructionLength(int opcode) {
        if (opcode == IN || opcode == OUT) {
            return 2;
        } else if (opcode == JF || opcode == JT) {
            return 3;
        } else if (opcode == HALT) {
            return 1;
        }
        return 4;
    }

    private boolean invalidParamModes(int[] paramModes) {
        for (int paramMode : paramModes) {
            if (!(paramMode == MODE_POSITION || paramMode == MODE_IMMEDIATE)) {
                return true;
            }
        }
        return false;
    }

    public void setProgram(int[] program) {
        this.program = program;
    }

    public BlockingQueue<Integer> getInputQueue() {
        return inputQueue;
    }

    public BlockingQueue<Integer> getOutputQueue() {
        return outputQueue;
    }
}
