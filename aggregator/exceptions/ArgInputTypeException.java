package com.alexander.aggregator.exceptions;

public class ArgInputTypeException extends Exception {
    /**
     * Error text
     */
    private String err = "Wrong type of input file! Should be xml or csv";

    @Override
    public String toString() { return err; }

    @Override
    public Throwable fillInStackTrace() { return null; }
}
