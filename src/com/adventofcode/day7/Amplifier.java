package com.adventofcode.day7;

import com.adventofcode.day2.IntcodeComputer;
import com.adventofcode.day2.exceptions.InvalidModeException;
import com.adventofcode.day2.exceptions.InvalidOpcodeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Amplifier implements Runnable {
    private Amplifier nextAmp;
    private int inputSignal;
    private int outputSignal;
    private int phaseSetting;
    private int[] intcodeProgram;
    private Queue<Integer> inputQueue;
    private Queue<Integer> outputQueue;

    public Amplifier(int[] intcodeProgram, int phaseSetting) {
        this.intcodeProgram = intcodeProgram;
        this.phaseSetting = phaseSetting;
    }

    public int amplify(Queue<Integer> inputQueue) throws InvalidOpcodeException, InvalidModeException {
        this.inputQueue = inputQueue;
        return amplify();
    }

    public int amplify() throws InvalidOpcodeException, InvalidModeException {
        //System.out.println("Amplifier " + phaseSetting + " executing");
        inputQueue = new LinkedList<>(List.of(phaseSetting, inputSignal));
        outputQueue = new LinkedList<>();

        intcodeProgram = IntcodeComputer.parseIntcode(
                intcodeProgram,
                IntcodeComputer.INPUT_MODE_QUEUE,
                IntcodeComputer.OUTPUT_MODE_QUEUE,
                inputQueue,
                outputQueue);

        outputSignal = outputQueue.poll();
        if (nextAmp == null) {
            return outputSignal;
        } else {
            return nextAmp.amplify(outputSignal);
        }
    }

    public void run() {
        inputQueue = new ArrayBlockingQueue<Integer>(10);
        outputQueue = new ArrayBlockingQueue<Integer>(10);

        try {
            intcodeProgram = IntcodeComputer.parseIntcode(
                    intcodeProgram,
                    IntcodeComputer.INPUT_MODE_QUEUE,
                    IntcodeComputer.OUTPUT_MODE_QUEUE,
                    inputQueue,
                    outputQueue);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Queue<Integer> getInputQueue() {
        return inputQueue;
    }

    public void setInputQueue(Queue<Integer> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public Queue<Integer> getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(Queue<Integer> outputQueue) {
        this.outputQueue = outputQueue;
    }

    public Amplifier getNextAmp() {
        return nextAmp;
    }

    public void setNextAmp(Amplifier nextAmp) {
        this.nextAmp = nextAmp;
    }

    public int getInputSignal() {
        return inputSignal;
    }

    public void setInputSignal(int inputSignal) {
        this.inputSignal = inputSignal;
    }

    public int getOutputSignal() {
        return outputSignal;
    }

    public void setOutputSignal(int outputSignal) {
        this.outputSignal = outputSignal;
    }
}
