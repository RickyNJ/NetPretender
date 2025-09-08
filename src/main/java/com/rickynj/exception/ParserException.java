package com.rickynj.exception;

public class ParserException extends RuntimeException {
    public ParserException(String message, Exception cause) {
        super(message, cause);
    }
}
