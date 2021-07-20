package com.msampietro.springbddintegrationtesting.exception;

public class InvalidTokenException extends RuntimeException {
    private final String[] args;

    public InvalidTokenException(String message) {
        super(message);
        this.args = null;
    }

    public InvalidTokenException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public String[] getArgs() {
        return this.args;
    }
}