package com.rickynj.exception;

public class ParserError extends RuntimeException {
    public ParserError(String message) {
        super(message);
    }
}
