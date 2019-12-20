package com.adventofcode.day2.exceptions;

public class InvalidParameterModeException extends Exception {
    public InvalidParameterModeException() {
        super();
    }
    public InvalidParameterModeException(String text) {
        super(text);
    }
}
