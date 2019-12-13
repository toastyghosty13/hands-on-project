package com.trilogyed.clientservice.exception;

public class InvalidCardNumber extends RuntimeException {
    public InvalidCardNumber(String message) {
        super("Message from InvalidCardNumber: " + message);
    }
}
