package com.musadzeyt.momentumapi.exception;

public class GeneralException extends RuntimeException {
    public GeneralException() {
        super("Record not found");
    }

    public GeneralException(String message) {
        super(message);
    }
}
