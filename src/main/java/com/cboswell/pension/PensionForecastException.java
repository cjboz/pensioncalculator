package com.cboswell.pension;

/**
 * Exception class for handling errors thrown when forecasting a pension
 */
public class PensionForecastException extends RuntimeException {

    public PensionForecastException(final String message) {
        super(message);
    }

    public PensionForecastException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
