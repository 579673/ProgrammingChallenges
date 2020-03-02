package com.adventofcode.day2.exceptions;

public class InvalidModeException extends RuntimeException {
    public InvalidModeException() {
        super();
    }
    public InvalidModeException(String text) {
        super(text);
    }
}
