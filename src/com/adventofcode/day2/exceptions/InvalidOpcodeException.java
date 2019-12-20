package com.adventofcode.day2.exceptions;

public class InvalidOpcodeException extends Exception {
    public InvalidOpcodeException() {
        super();
    }
    public InvalidOpcodeException(String text) {
        super(text);
    }
}
