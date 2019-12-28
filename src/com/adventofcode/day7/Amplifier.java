package com.adventofcode.day7;

import com.adventofcode.day2.IntcodeComputer;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Amplifier extends Thread {
    private BlockingQueue<Integer> inputQueue;
    private BlockingQueue<Integer> outputQueue;

    public Amplifier(int[] intcodeProgram, BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue) {
        super(new IntcodeComputer(
                intcodeProgram,
                IntcodeComputer.INPUT_MODE_QUEUE,
                IntcodeComputer.OUTPUT_MODE_QUEUE,
                inputQueue,
                outputQueue));
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public Queue<Integer> getInputQueue() {
        return inputQueue;
    }

    public void setInputQueue(BlockingQueue<Integer> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public Queue<Integer> getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(BlockingQueue<Integer> outputQueue) {
        this.outputQueue = outputQueue;
    }
}
