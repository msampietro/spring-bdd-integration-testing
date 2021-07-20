package com.msampietro.springbddintegrationtesting.exception;

public class ObjectNotValidException extends Exception {
    private final String[] args;

    public ObjectNotValidException(String message) {
        super(message);
        this.args = null;
    }

    public ObjectNotValidException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public String[] getArgs() {
        return this.args;
    }
}