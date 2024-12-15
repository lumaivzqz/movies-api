package com.eron.moviesapi.exceptions;

public class EronApiException extends RuntimeException {
    public static String MESSAGE = "Threshold must be a positive integer.";

    public EronApiException(String errorMessage, Exception e) {
        super(errorMessage);
    }
}
