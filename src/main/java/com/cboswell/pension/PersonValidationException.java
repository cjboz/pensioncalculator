package com.cboswell.pension;

/**
 * Custom Exception Class for Person Validation Errors
 */
public class PersonValidationException extends RuntimeException {

    public PersonValidationException(final String message) {
        super(message);
    }
}
