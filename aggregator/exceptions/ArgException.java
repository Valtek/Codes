package com.alexander.aggregator.exceptions;

public class ArgException extends Exception {
    /**
     * Error text
     */
    private String err = "Invalid arguments!";

    @Override
    public String toString() { return err; }

    @Override
    public Throwable fillInStackTrace() { return null; }
}

