package com.musadzeyt.momentumapi.exception;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException() {
        super("Activity not found");
    }

    public ActivityNotFoundException(String message) {
        super(message);
    }
}
