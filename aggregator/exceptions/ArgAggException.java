package com.alexander.aggregator.exceptions;

public class ArgAggException extends Exception {
    /**
     * Error text
     */
    private String err = "Invalid aggregation arguments!";

    @Override
    public String toString() { return err; }

    @Override
    public Throwable fillInStackTrace() { return null; }
}
