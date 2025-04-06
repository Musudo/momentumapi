package com.musadzeyt.momentumapi.exception;

// TODO: remove this -> EntityNotFoundException is just enough
public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException() {
        super("Activity not found");
    }

    public ActivityNotFoundException(String message) {
        super(message);
    }
}
