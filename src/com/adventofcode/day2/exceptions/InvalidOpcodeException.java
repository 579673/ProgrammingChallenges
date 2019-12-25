package com.adventofcode.day2.exceptions;

public class InvalidOpcodeException extends RuntimeException {
    public InvalidOpcodeException() {
        super();
    }
    public InvalidOpcodeException(String text) {
        super(text);
    }
}
