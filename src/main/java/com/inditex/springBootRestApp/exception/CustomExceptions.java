package com.inditex.springBootRestApp.exception;

public class CustomExceptions {

    public static class PriceNotFoundException extends RuntimeException {
        public PriceNotFoundException(String message) {
            super(message);
        }
    }
}