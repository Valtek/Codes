package com.alexander.aggregator.exceptions;

public class ArgNumException extends Exception {
    /**
     * Error text
     */
    private String err = "Wrong number of arguments!";

    @Override
    public String toString() { return err; }

    @Override
    public Throwable fillInStackTrace() { return null; }
}
