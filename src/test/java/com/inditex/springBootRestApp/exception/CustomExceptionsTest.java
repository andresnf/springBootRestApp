package com.inditex.springBootRestApp.exception;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionsTest {

    @Test
    public void testPriceNotFoundException() {
        String errorMessage = "Price not found";
        CustomExceptions.PriceNotFoundException exception = new CustomExceptions.PriceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }
}