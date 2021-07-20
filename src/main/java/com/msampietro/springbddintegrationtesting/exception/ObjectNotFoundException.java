package com.msampietro.springbddintegrationtesting.exception;

public class ObjectNotFoundException extends Exception {
    private final String[] args;

    public ObjectNotFoundException(String message) {
        super(message);
        this.args = null;
    }

    public ObjectNotFoundException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public String[] getArgs() {
        return this.args;
    }
}
