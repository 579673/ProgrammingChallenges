package com.adventofcode.day7;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AmplificationCircuit {
    private Amplifier first;

    public AmplificationCircuit(int[] intcodeProgram, int[] phaseSettingOrder) {
        Amplifier prevAmp = null;
        for (int phaseSetting : phaseSettingOrder) {
            BlockingQueue<Integer> inputQueue = new ArrayBlockingQueue<>(10, true);
            BlockingQueue<Integer> outputQueue = new ArrayBlockingQueue<>(10, true);
            inputQueue.add(phaseSetting);


            Amplifier amp = new Amplifier(
                    intcodeProgram,
                    inputQueue,
                    outputQueue);

            if (first == null) {
                first = amp;
            }

            prevAmp = amp;
        }
    }

    public int startAmplification(int inputSignal) {

    }
}
