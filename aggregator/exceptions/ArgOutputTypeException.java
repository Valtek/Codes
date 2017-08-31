package com.alexander.aggregator.exceptions;

public class ArgOutputTypeException extends Exception {
    /**
     * Error text
     */
    private String err = "Wrong type of output file! Should be xml or csv or db";

    @Override
    public String toString() { return err; }

    @Override
    public Throwable fillInStackTrace() { return null; }
}
