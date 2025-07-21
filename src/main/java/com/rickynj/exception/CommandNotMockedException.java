package com.rickynj.exception;

public class CommandNotMockedException extends RuntimeException {
    public CommandNotMockedException(String message) {
        super(message);
    }
}
