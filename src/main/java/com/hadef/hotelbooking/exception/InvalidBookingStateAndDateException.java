package com.hadef.hotelbooking.exception;

public class InvalidBookingStateAndDateException extends RuntimeException{
    public InvalidBookingStateAndDateException(String message) {
        super(message);
    }
}
