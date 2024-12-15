package com.eron.moviesapi.exceptions;

public class InvalidThresholdException extends RuntimeException {
    public static String MESSAGE = "Threshold must be a positive integer.";

    public InvalidThresholdException() {
        super(MESSAGE);
    }
}
