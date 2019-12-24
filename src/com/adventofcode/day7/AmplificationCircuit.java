package com.adventofcode.day7;

import com.adventofcode.day2.exceptions.InvalidModeException;
import com.adventofcode.day2.exceptions.InvalidOpcodeException;

public class AmplificationCircuit {
    private Amplifier first;

    public AmplificationCircuit(int[] intcodeProgram, int[] phaseSettingOrder) {
        Amplifier prevAmp = null;
        for(int phaseSetting : phaseSettingOrder) {
            if(prevAmp == null) {
                first = new Amplifier(intcodeProgram, phaseSetting);
                prevAmp = first;
            } else {
                Amplifier newAmp = new Amplifier(intcodeProgram, phaseSetting);
                prevAmp.setNextAmp(newAmp);
                prevAmp = newAmp;
            }
        }
    }

    public void initiateFeedbackLoop() {
        Amplifier temp = first;
        while (temp.getNextAmp() != null) {
            temp = temp.getNextAmp();
        }
        temp.setNextAmp(first);
    }

    public int startAmplification(int inputSignal) throws InvalidModeException, InvalidOpcodeException {
        return first.amplify(inputSignal);
    }
}
